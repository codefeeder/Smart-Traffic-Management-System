from flask import Flask
from yolo.yolo import detectfinal

app = Flask(__name__)

@app.route('/inf')
def inf():
    x = 0
    while(True):
        list = detectfinal(x)
        print(list)
        x += 1
    # print(detectfinal(1))
    # print(detectfinal(0))
    return 'yolo'

if __name__ == "__main__":
    app.run(host='127.0.0.1', port=3000)