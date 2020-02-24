<p align="center">
  <br>
    <img src="https://admin.peacemakr.io/images/PeacemakrP-Golden.png" width="150"/>
  <br>
</p>

# Peacemakr Java Tools

These tools simplify integration with Peacemakr's Secure Data Platform.

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

Peacemakr welcomes open and active contributions to this SDK. As long as they're in the spirit of project, we will most 
likely accept them. However, you may want to get our opinion on proposed changes before investing time, so we can work 
together to solve problems you encounter that make sense for the future direction we have planned.

## Testing

We use the usual fork and PR mechanisms, and in this section, here are some basic guidelines on how to setup a 
development environment. Without being a member of peacemakr, you will not have full access to the testing infrastructure 
required for complete code coverage, but our CircleCI build and test pipeline can be used to provide this level of 
visibility and provide feedback.
