# Java-SSH

A SSH Library that encapsulate jsch for simple commands usage.

## Sample

```java
SSHUserInfo userInfo = new SSHUserInfo("user","password");
SSHService ssh = new SSHService(userInfo, "192.168.0.100");


ssh.connect();

ssh.exec("cd ~/logs/");
String tailLog = ssh.exec("tail service.log");

ssh.close();

```
