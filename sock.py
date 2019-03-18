import socket

HOST = '192.168.43.50'
PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((HOST, PORT))
print('Socket bind success!')

s.listen(10)
print('Socket is now listening')

arr = []

while 1:
    conn, addr = s.accept()
    print('Connect with ' + addr[0] + ':' + str(addr[1]))
    buffer = conn.recv(64)
    if buffer:
        arr.append(buffer)
    if len(arr) == 6:
        with open('arr.txt', 'w') as f:
            for item in arr:
                f.write("%s\n" % item)
s.close()