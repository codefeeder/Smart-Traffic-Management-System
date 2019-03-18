import os

import cv2
import yolo.logic as logic
from yolo.helper import *
import yolo.computerVision as computerVision

# load the COCO class labels our YOLO model was trained on
labelsPath = os.path.sep.join([os.path.dirname(os.path.abspath(__file__)), "yolo-coco", "coco.names"])
LABELS = open(labelsPath).read().strip().split("\n")

# derive the paths to the YOLO weights and model configuration
weightsPath = os.path.sep.join([os.path.dirname(os.path.abspath(__file__)), "yolo-coco", "yolov3.weights"])
configPath = os.path.sep.join([os.path.dirname(os.path.abspath(__file__)), "yolo-coco", "yolov3.cfg"])

net = cv2.dnn.readNetFromDarknet(configPath, weightsPath)


# load our input image and grab its spatial dimensions
def detect(imgpath, confindence=0.5, threshold=0.3):
    image = cv2.imread(imgpath)
    computerVision.filename = imgpath
    result = computerVision.runCV()
    if (result):
        return 10e8
    # determine only the *output* layer names that we need from YOLO
    ln = net.getLayerNames()
    ln = [ln[i[0] - 1] for i in net.getUnconnectedOutLayers()]

    # construct a blob from the input image and then perform a forward
    # pass of the YOLO object detector, giving us our bounding boxes and
    # associated probabilities
    blob = cv2.dnn.blobFromImage(image, 1 / 255.0, (416, 416),
                                 swapRB=True, crop=False)
    net.setInput(blob)
    # start = time.time()
    layerOutputs = net.forward(ln)
    # end = time.time()

    # show timing information on YOLO
    # print("[INFO] YOLO took {:.6f} seconds".format(end - start))

    # initialize our lists of detected bounding boxes, confidences, and
    # class IDs, respectively
    return show_result(layerOutputs, confindence, threshold, image)


def getFrameHelper(videoPath):
    vs = cv2.VideoCapture(videoPath)
    arr = videoPath.split('\\')
    arr = arr[len(arr) - 1]
    arr = arr.split('.')
    hola = arr[0]
    count = 0
    while True:
        (grabbed, frame) = vs.read()
        if not grabbed or count > 700:
            break
        cv2.imwrite(f'frames/{hola}/{count}.jpg', frame)
        count = count + 1


def detectfinal(iter):
    imglist = []
    for i in range(2):
        imglist.append(
            os.path.sep.join([os.path.dirname(os.path.abspath(__file__)), "frames", f'{i + 1}', f'{iter + 10}' + '.jpg']))
        imglist.append(
            os.path.sep.join(
                [os.path.dirname(os.path.abspath(__file__)), "frames", f'{i + 3}', f'{iter + 360}' + '.jpg']))
    finalList = detectFour(imglist)

    return logic.conclusion(finalList)

    # return finalList


def detectFour(imglist):
    ra = []
    for i in range(len(imglist)):
        ra.append(detect(imglist[i]))
    return ra


def show_result(layerOutputs, confidence, threshold, image):
    boxes = []
    confidences = []
    classIDs = []
    (h, w) = image.shape[:2]

    # loop over each of the layer outputs
    for output in layerOutputs:
        # loop over each of the detections
        for detection in output:
            # extract the class ID and confidence (i.e., probability) of
            # the current object detection
            scores = detection[5:]
            classID = np.argmax(scores)
            cf = scores[classID]

            # filter out weak predictions by ensuring the detected
            # probability is greater than the minimum probability
            if cf > confidence:
                # scale the bounding box coordinates back relative to the
                # size of the image, keeping in mind that YOLO actually
                # returns the center (x, y)-coordinates of the bounding
                # box followed by the boxes' width and height
                box = detection[0:4] * np.array([w, h, w, h])
                (centerX, centerY, width, height) = box.astype("int")

                # use the center (x, y)-coordinates to derive the top and
                # and left corner of the bounding box
                x = int(centerX - (width / 2))
                y = int(centerY - (height / 2))

                # update our list of bounding box coordinates, confidences,
                # and class IDs
                boxes.append([x, y, int(width), int(height)])
                confidences.append(float(cf))
                classIDs.append(classID)

    # apply non-maxima suppression to suppress weak, overlapping bounding
    # boxes
    idxs = cv2.dnn.NMSBoxes(boxes, confidences, confidence,
                            threshold)

    # ensure at least one detection exists
    objects = {'car', 'truck', 'bus', 'bicycle', 'motorbike'}
    ann = []
    if len(idxs) > 0:
        # loop over the indexes we are keeping
        for i in idxs.flatten():
            if LABELS[classIDs[i]] not in objects:
                continue

            # draw a bounding box rectangle and label on the image
            ann.append(hw_bb(boxes[i]))

    # show the output image
    draw_im(image, ann)
    plt.savefig('result.png')
    return len(ann)


# print(detectfinal(0))
# getFrameHelper(os.path.sep.join([os.path.dirname(os.path.abspath(__file__)), "videos_raw", "3.mp4"]))

# detect('images/1.jpg')
