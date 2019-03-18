import datetime
import time
r1 = datetime.datetime.now()
time.sleep(5)
r2 = datetime.datetime.now()
print (r1)
print (r2)
elapsed_time = r2-r1
ans = divmod(elapsed_time.total_seconds(), 60)
print(ans[0])
