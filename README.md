<p align="center">
  <br>
    <img src="https://admin.peacemakr.io/images/PeacemakrP-Golden.png" width="150"/>
  <br>
</p>

![Build and Test](https://github.com/peacemakr-io/peacemakr-java-tools/workflows/Build%20and%20Test/badge.svg?branch=master)

# Peacemakr Java Tools

These tools simplify integration with Peacemakr's Secure Data Platform, specifically the [Java SDK](https://github.com/peacemakr-io/peacemakr-java-sdk)

## Quick Start

Use the latest release of the [Peacemakr Java SDK](https://github.com/peacemakr-io/peacemakr-java-sdk) and
the latest release of this package
```xml
<dependency>
    <groupId>io.peacemakr</groupId>
    <artifactId>peacemakr-java-sdk</artifactId>
    <version>0.0.2</version>
</dependency>
<dependency>
    <groupId>io.peacemakr</groupId>
    <artifactId>peacemakr-java-tools</artifactId>
    <version>0.0.1</version>
</dependency>
```

Then, you might do something like this in your code to define and encrypt some fields of a data class
```java
import io.peacemakr.annotations.Encrypted;
import io.peacemakr.crypto.tools.Encryptor;
import io.peacemakr.crypto.Factory;
import io.peacemakr.crypto.ICrypto;
import io.peacemakr.crypto.impl.persister.InMemoryPersister;

private class Message {
    @Encrypted
    public String secretString;
    
    @Encrypted
    public byte[] secretBytes;
    
    public String publicString;
}

...
TestMessage msg = new TestMessage();
// ... fill message here
ICrypto p = Factory.getCryptoSDK("your API key", "your client name", null, new InMemoryPersister(), null);
Encryptor<TestMessage> encryptor = new Encryptor<>(TestMessage.class, p);

encryptor.encrypt(msg);
```
And maybe something like this to decrypt those fields
```java
// ... receive encrypted message from outside
encryptor.decrypt(msg);
```
To see a more concrete example, please check out the tests.

## Contributions

Peacemakr welcomes open and active contributions to this toolkit. As long as they're in the spirit of project, we will most 
likely accept them. However, you may want to get our opinion on proposed changes before investing time, so we can work 
together to solve problems you encounter in a way that makes sense for the future direction we have planned.

## Testing

Without being a member of Peacemakr, you will not have full access to the testing infrastructure required for complete 
code coverage, but our CI build and test pipeline can be used to provide this level of visibility and feedback.

## Releasing
Prerequisites:
- Maven to be configured with Java8
- Peacemakr GPG key

OSSRH:
- add the following to `~/.m2/settings.xml`; replace `user_id` and `pass` with proper values.
```aidl
<settings>
  <servers>
    <server>
      <id>ossrh</id>
      <username>user_id</username>
      <password>pass</password>
    </server>
  </servers>
</settings>
```
- release `mvn clean deploy -P ossrh`

Local repo:
- release `mvn clean install -P ossrh`

If the following exception occurs during the release:
```aidl
[INFO] --- maven-gpg-plugin:1.5:sign (sign-artifacts) @ crypto.tools ---
gpg: signing failed: Inappropriate ioctl for device
```
The following is the fix:
```aidl
export GPG_TTY=$(tty) 
```