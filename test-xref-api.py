import requests
import subprocess
import time


def main():
    build = 0
    while 1:
        print("Polling build...")
        url = "https://xref-api.spstest.in/up"
        get = requests.get(url)
        print(get)
        if str(get) == '<Response [200]>':
            res = get.json()
            if build != res["build"]:
                print(res["build"])
                build = res["build"]
                subprocess.call(['./test-xref-api.sh'])
        else:
            print('xref-api isnt running')
        time.sleep(5)

main()
