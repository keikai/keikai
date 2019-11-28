# Release Process
1. update version 
 `bin/upVer.sh oldVersion newVersion`
2. build at local <br/>
`bin/release.sh version` <br/>
it will build maven bundle and binary zip and upload to fileserver at `/potix/rd/keikaioss/releases/[version]`
4. [publish to repo](http://jenkins2/view/Keikai-5.0/job/Maven%20Upload%20Keikai/)

## Verification
test without the original local repository to simulate a new user

`mvn jetty:run -Dmaven.repo.local=/tmp`