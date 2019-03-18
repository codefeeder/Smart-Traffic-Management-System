import http.client, urllib.request, urllib.parse, urllib.error, base64

filename = None
headers = {
    # Request headers
    'Content-Type': 'application/octet-stream',
    'Ocp-Apim-Subscription-Key': '54ea2eeb4e804346bb5f5017c3e298b5',
}

params = urllib.parse.urlencode({
    # Request parameters
    'visualFeatures': 'Tags',
    'language': 'en',
})


def runCV():
    with open(filename, 'rb') as f:
        img_data = f.read()
    conn = http.client.HTTPSConnection('eastasia.api.cognitive.microsoft.com')
    conn.request("POST", "/vision/v2.0/analyze?%s" % params, img_data, headers)
    response = conn.getresponse()
    data = response.read()
    result = False
    import json
    try:
        data_1 = (json.loads(data)['tags'])
    except:
        return False
    for i in data_1:
        if 'ambulance' == i['name']:
            result = True
            break
    conn.close()
    return result
