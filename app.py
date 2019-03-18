from flask import Flask
import random
import requests
import datetime
import time
import json
from yolo.logic import time_update

app = Flask(__name__)

api_key = 'up87un4nhriz1agk873vtsjwppy47hgd'

url1 = f'http://apis.mapmyindia.com/advancedmaps/v1/{api_key}/distance?center=12.921288,77.668916%7C&pts=12.922310,77.668916'
url2 = f'http://apis.mapmyindia.com/advancedmaps/v1/{api_key}/distance?center=12.922310,77.668916%7C&pts=12.922610,77.669872'
url3 = f'http://apis.mapmyindia.com/advancedmaps/v1/{api_key}/distance?center=12.922610,77.669872%7C&pts=12.923450,77.671286'
url4 = f'http://apis.mapmyindia.com/advancedmaps/v1/{api_key}/distance?center=12.923450,77.671286%7C&pts=12.925450,77.671286'
url5 = f'http://apis.mapmyindia.com/advancedmaps/v1/{api_key}/distance?center=12.925450,77.671286%7C&pts=12.924118,77.673142'

@app.route('/api')
def api():
    while(True):
        severity = random.random()

        body = requests.get(url1)
        body = json.loads(body.text)
        time_update(int(int(body["results"][0]["duration"])/7), severity)
        time.sleep(int(int(body["results"][0]["duration"])/7))
        body = requests.get(url2)
        body = json.loads(body.text)
        time_update(int(int(body["results"][0]["duration"])/7), severity)
        time.sleep(int(int(body["results"][0]["duration"])/7))
        body = requests.get(url3)
        body = json.loads(body.text)
        time_update(int(int(body["results"][0]["duration"])/7), severity)
        time.sleep(int(int(body["results"][0]["duration"])/7))
        body = requests.get(url4)
        body = json.loads(body.text)
        time_update(int(int(body["results"][0]["duration"])/7), severity)
        time.sleep(int(int(body["results"][0]["duration"])/7))
        body = requests.get(url5)
        body = json.loads(body.text)
        time_update(int(int(body["results"][0]["duration"])/7), severity)
        time.sleep(int(int(body["results"][0]["duration"])/7))
    return 'hello world'

if __name__ == "__main__":
    app.run(host='127.0.0.1', port=4000)
