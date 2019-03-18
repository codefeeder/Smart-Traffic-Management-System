import datetime
import random
import numpy as np
import io
import pickle
arr=[]
tr = 0
arr.append(tr) #0
time_at_recieved = datetime.datetime.now()
arr.append(time_at_recieved)#1
severity_index = 0
arr.append(severity_index)#2
tr_1 = 0
arr.append(tr_1)#3
time_at_recieved_1 = datetime.datetime.now()
arr.append(time_at_recieved_1)#4
severity_index_1 = 0
arr.append(severity_index_1)#5
crossing = 1
arr.append(crossing)#6
signal = random.randrange(0, 4)
arr.append(signal)#7
arr=np.array(arr)
compressed_array = io.BytesIO()
# np.savez_compressed(compressed_array,arr)
with open('filename.pickle', 'wb') as handle:
    pickle.dump(arr, handle, protocol=pickle.HIGHEST_PROTOCOL)

def time_update(new_time, severity):
    global tr
    global tr_1
    global time_at_recieved
    global severity_index
    global time_at_recieved_1
    global severity_index_1
    global crossing
    global signal
    # arr = np.load(compressed_array)['arr_0']
    with open('filename.pickle', 'rb') as handle:
        arr = pickle.load(handle)
    arr = arr.tolist()
    tr_1 = new_time
    arr[3] = tr_1
    time_at_recieved_1 = datetime.datetime.now()
    arr[4] = time_at_recieved_1
    severity_index_1 = severity
    arr[5] = severity_index_1
    arr=np.array(arr)
    # np.savez_compressed(compressed_array,arr)
    with open('filename.pickle', 'wb') as handle:
        pickle.dump(arr, handle, protocol=pickle.HIGHEST_PROTOCOL)


def conclusion(density):
    global tr
    global tr_1
    global time_at_recieved
    global severity_index
    global time_at_recieved_1
    global severity_index_1
    global crossing
    global signal
    # arr = np.load(compressed_array)['arr_0']
    with open('filename.pickle', 'rb') as handle:
        arr = pickle.load(handle)
    arr = arr.tolist()
# tr = 0
# arr.append(tr) #0
# time_at_recieved = datetime.datetime.now()
# arr.append(time_at_recieved)#1
# severity_index = 0
# arr.append(severity_index)#2
# tr_1 = 0
# arr.append(tr_1)#3
# time_at_recieved_1 = datetime.datetime.now()
# arr.append(time_at_recieved_1)#4
# severity_index_1 = 0
# arr.append(severity_index_1)#5
# crossing = 1
# arr.append(crossing)#6
# signal = random.randrange(0, 4)
# arr.append(signal)#7
    tr = arr[0]
    time_at_recieved = arr[1]
    severity_index = arr[2]
    tr_1 = arr[3]
    time_at_recieved_1 = arr[4]
    severity_index_1 = arr[5]
    crossing = arr[6]
    signal = arr[7]

    if tr == 0:
        if tr_1 != 0:
            tr = tr_1
            time_at_recieved = time_at_recieved_1
            severity_index = severity_index_1
            tr_1 = 0
            time_at_recieved_1 = 0
            severity_index_1 = 0
            # arr = np.load(compressed_array)['arr_0']
            with open('filename.pickle', 'rb') as handle:
                arr = pickle.load(handle)
            arr = arr.tolist()
            arr[0] = tr
            arr[1] = time_at_recieved
            arr[2] = severity_index
            arr[3] = tr_1
            arr[4] = time_at_recieved_1
            arr[5] = severity_index_1
            arr=np.array(arr)
            # np.savez_compressed(compressed_array,arr)
            with open('filename.pickle', 'wb') as handle:
                pickle.dump(arr, handle, protocol=pickle.HIGHEST_PROTOCOL)

        crossing_str = str(crossing)
        #print ('hi')
        crossing_str = 'crossing - ' + crossing_str
        print (" \nInstruction on the basis of Congestion Density ")
        ret = [crossing_str, ' No Emergency vehicle approaching ', 'R', 'R', 'R', 'R']
        i = 0
        max = -1
        for k in range(4):
            if density[k] > max:
                max = density[k]
                i = k
        ret[2] = ret[2] + ' - ' + str(density[0])
        ret[3] = ret[3] + ' - ' + str(density[1])
        ret[4] = ret[4] + ' - ' + str(density[2])
        ret[5] = ret[5] + ' - ' + str(density[3])
        ret[i + 2] = 'G - ' + str(density[i])
        return ret
    else:
        time_now = datetime.datetime.now()
        elapsed_time = time_now - time_at_recieved
        diff = divmod(elapsed_time.total_seconds(), 60)
        diff_sec = diff[0] * 60 + diff[1]
        in_time = tr - diff_sec
        if (tr - diff_sec) < 5:
            signal_now = signal
            if (tr <= diff_sec):
                crossing = crossing + 1
                tr = 0
                time_at_recieved = 0
                severity_index = 0
                signal = random.randrange(0, 4)
                # arr = np.load(compressed_array)['arr_0']
                with open('filename.pickle', 'rb') as handle:
                    arr = pickle.load(handle)
                arr = arr.tolist()
                arr[6] = crossing
                arr[0] = tr
                arr[2] = severity_index
                arr[1] = time_at_recieved
                arr[7] = signal
                arr=np.array(arr)
                # np.savez_compressed(compressed_array,arr)
                with open('filename.pickle', 'wb') as handle:
                    pickle.dump(arr, handle, protocol=pickle.HIGHEST_PROTOCOL)
            crossing_str = str(crossing)
            in_time = round (in_time,2)
            if (in_time<0):
                in_time = 0
            in_time_str = str(in_time)

            print (' \nInstruction according to the approaching ambulance ')
            signal_now_str = str(signal_now+1)
            in_time_str = in_time_str + ' to reach signal ' + signal_now_str
            crossing_str = 'crossing - ' + crossing_str
            ret = [crossing_str, in_time_str, 'R', 'R', 'R', 'R']
            ret[2] = ret[2] + ' - ' + str(density[0])
            ret[3] = ret[3] + ' - ' + str(density[1])
            ret[4] = ret[4] + ' - ' + str(density[2])
            ret[5] = ret[5] + ' - ' + str(density[3])
            ret[signal_now + 2] = 'G - ' + str(density[signal_now])
            return ret
        else:
            time_now = datetime.datetime.now()
            elapsed_time = time_now - time_at_recieved
            diff = divmod(elapsed_time.total_seconds(), 60)
            diff_sec = diff[0] * 60 + diff[1]
            tre_1 = density[signal] + severity_index * 10 * (1 / (diff_sec))
            i = 0
            tr_2 = -1
            for k in range(4):
                if density[k] > tr_2:
                    tr_2 = density[k]
                    i = k
            if tr_2 > tre_1:
                print (" \nInstruction on the basis of Congestion Density ")
                crossing_str = str(crossing)
                crossing_str = 'crossing - ' + crossing_str
                in_time = round (in_time,2)
                if (in_time<0):
                    in_time = 0
                in_time_str = str(in_time)
                signal_now_str = str(signal+1)
                in_time_str = in_time_str + ' to reach signal ' + signal_now_str
                ret = [crossing_str, in_time_str, 'R', 'R', 'R', 'R']
                ret[2] = ret[2] + ' - ' + str(density[0])
                ret[3] = ret[3] + ' - ' + str(density[1])
                ret[4] = ret[4] + ' - ' + str(density[2])
                ret[5] = ret[5] + ' - ' + str(density[3])
                ret[i + 2] = 'G - ' + str(density[i])
            else:
                print (' \nInstruction according to the approaching ambulance ')
                crossing_str = str(crossing)
                crossing_str = 'crossing - ' + crossing_str
                in_time = round (in_time,2)
                if (in_time<0):
                    in_time = 0
                in_time_str = str(in_time)
                signal_now_str = str(signal+1)
                in_time_str = in_time_str + ' to reach signal ' + signal_now_str
                ret = [crossing_str, in_time_str, 'R', 'R', 'R', 'R']
                ret[2] = ret[2] + ' - ' + str(density[0])
                ret[3] = ret[3] + ' - ' + str(density[1])
                ret[4] = ret[4] + ' - ' + str(density[2])
                ret[5] = ret[5] + ' - ' + str(density[3])
                ret[signal + 2] = 'G - ' + str(density[signal])
            return ret


# initialize()

# print(conclusion([10, 20, 30, 4]))
# time_update(80,0)
# print (tr_1)
