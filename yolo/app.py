from flask import Flask, render_template, request, send_from_directory
from yolo import *

app = Flask(__name__)

UPLOAD_FOLDER = os.path.basename('uploads')
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


@app.route('/')
def hello_world():
    return render_template('index.html')


@app.route('/upload', methods=['POST'])
def upload_file():
    file = request.files['image']
    f = os.path.join(os.path.dirname(os.path.abspath(__file__)), app.config['UPLOAD_FOLDER'], file.filename)
    file.save(f)
    density = detect(f)
    return render_template('download.html', variable=density)



@app.route('/return-files')
def return_files_tut():
    return send_from_directory(os.path.dirname(os.path.abspath(__file__)), 'result.png', as_attachment=True)


if __name__ == "__main__":
    app.run()
