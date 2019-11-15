# Release Process
1. update version 
 `bin/upVer.sh oldVersion newVersion`
2. build at local <br/>
`bin/release.sh version` <br/>
it will upload to fileserver at `/potix/rd/keikaioss/releases/[version]`
4. [publish to repo](http://jenk``ins2/view/Keikai-5.0/job/Maven%20Upload%20Keikai/)