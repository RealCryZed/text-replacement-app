# To run this project
* Download this project to your local machine
* Open cmd and go to the root folder of this application
* Run `docker build -t realcryzed/textreplacement-build-and-run .`
* Run `docker run --rm -v "PATH_TO_YOUR_FILE":"PATHNAME_YOU_WANT" -it realcryzed/textreplacement-build-and-run:latest`
* Example: `PATH_TO_YOUR_FILE` can be "C:\Users\user\Desktop\text-folder", `PATHNAME_YOU_WANT` can be "/docker-path"