package io.peacemakr.crypto.tools;

import io.peacemakr.annotations.Encrypted;
import io.peacemakr.crypto.Factory;
import io.peacemakr.crypto.ICrypto;
import io.peacemakr.crypto.impl.persister.InMemoryPersister;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class EncryptorTest {

  private class TestMessage {
    @Encrypted
    public String secretString;

    @Encrypted
    public byte[] secretBytes;

    public String publicString;
  }

  private byte[] getRandomBytes() {
    Random rd = new Random();
    byte[] arr = new byte[1024];
    rd.nextBytes(arr);
    return arr;
  }

  @Test
  public void test() throws Exception {

    TestMessage msg = new TestMessage();
    msg.secretString = "super secret string";
    byte[] bytes = getRandomBytes();
    msg.secretBytes = bytes;
    msg.publicString = "Hello there friend";

    ICrypto p = Factory.getCryptoSDK(null, null, null, new InMemoryPersister(), null);
    Encryptor<TestMessage> encryptor = new Encryptor<>(TestMessage.class, p);

    encryptor.encrypt(msg);

    assertEquals(msg.publicString, "Hello there friend");
    assertNotEquals(msg.secretString, "super secret string");
    assertFalse(Arrays.equals(msg.secretBytes, bytes));

    encryptor.decrypt(msg);

    assertEquals(msg.publicString, "Hello there friend");
    assertEquals(msg.secretString, "super secret string");
    assertArrayEquals(msg.secretBytes, bytes);

  }
}