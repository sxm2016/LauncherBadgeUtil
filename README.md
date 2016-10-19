本项目支持ADW、APEX、ASUS、LG、HTC（新版本）、Samsung、Solid、Sony、Xiaomi、Nova等手机的角标的展示<br />
本项目在参考BadgeUtil项目的基础上，进行修改<br />
小米手机规定，每一个notifyId消息，需要传递当前消息的未读消息的数量<br />
为此，本项目专门处理①服务器返回多种类型的消息，②只返回总未读数的情况下，兼容小米桌面角标的一个程序<br />
原理：小米手机收到一个类型的消息之后，会将该消息保存到一个队列里面，等小米手机再次收到其他类型的消息后，程序会读取队列列表，然后清空队列中非当前消息的未读数量<br />

参考：<br />
https://github.com/leolin310148/ShortcutBadger <br />
https://github.com/lixiangers/BadgeUtil