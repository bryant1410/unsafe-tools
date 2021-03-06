/*
 * Copyright 2013 Alex Kasko (alexkasko.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alexkasko.unsafe.offheapstruct;

import com.alexkasko.unsafe.bytearray.ByteArrayTool;
import com.alexkasko.unsafe.offheap.OffHeapUtils;
import org.junit.Test;

import static com.alexkasko.unsafe.offheap.OffHeapUtils.free;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: alexkasko
 * Date: 7/4/13
 */
public class OffHeapStructArrayListTest {
    private static final ByteArrayTool BT = ByteArrayTool.get();

    @Test
    public void test() {
        OffHeapStructArrayList list = null;
        try {
            ByteArrayTool bat = ByteArrayTool.get();
            list = new OffHeapStructArrayList(8);
            byte[] buf = new byte[8];
            for (int i = 0; i < 42; i++) {
                bat.putLong(buf, 0, i);
                list.add(buf);
            }
            bat.putLong(buf, 0, 1L << 42);
            list.add(buf);
            assertEquals("Size fail", 43, list.size());
            assertTrue("Capacity fail", list.capacity() >= 43);
            list.get(0, buf);
            assertEquals("Payload fail", 0, bat.getLong(buf, 0));
            list.get(41, buf);
            assertEquals("Payload fail", 41, bat.getLong(buf, 0));
            list.get(42, buf);
            assertEquals("Payload fail", 1L << 42, bat.getLong(buf, 0));
        } finally {
            free(list);
        }
    }

    @Test
    public void testReadByte() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            byte[] b = new byte[8];
            b[2] = (byte) 0x2a;
            b[3] = (byte) 0xd6;
            arr.set(0, b);
            assertEquals((byte) 0x2a, arr.getByte(0, 2));
            assertEquals((byte) 0xd6, arr.getByte(0, 3));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteByte() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            arr.putByte(0, 2, (byte) 0x2a);
            arr.putByte(0, 3, (byte) 0xd6);
            byte[] s = new byte[8];
            arr.get(0, s);
            assertEquals((byte) 0x2a, s[2]);
            assertEquals((byte) 0xd6, s[3]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadUnsignedByte() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            byte[] b = new byte[8];
            b[2] = (byte) 0xfe;
            b[3] = (byte) 0x2a;
            arr.set(0, b);
            assertEquals((short) 0xfe, arr.getUnsignedByte(0, 2));
            assertEquals((short) 0x2a, arr.getUnsignedByte(0, 3));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteUnsignedByte() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            arr.putUnsignedByte(0, 2, (short) 0xfe);
            arr.putUnsignedByte(0, 3, (short) 0x2a);
            byte[] s = new byte[8];
            arr.get(0, s);
            assertEquals((byte) 0xfe, s[2]);
            assertEquals((byte) 0x2a, s[3]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadShort() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            byte[] b = new byte[8];
            b[2] = (byte) 0x2a;
            b[3] = (byte) 0x7d;
            b[4] = (byte) 0xd6;
            b[5] = (byte) 0x82;
            arr.set(0, b);
            assertEquals((short) 0x7d2a, arr.getShort(0, 2));
            assertEquals((short) 0x82d6, arr.getShort(0, 4));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteShort() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            arr.putShort(0, 2, (short) 0x7d2a);
            arr.putShort(0, 4, (short) 0x82d6);
            byte[] s = new byte[8];
            arr.get(0, s);
            assertEquals((byte) 0x2a, s[2]);
            assertEquals((byte) 0x7d, s[3]);
            assertEquals((byte) 0xd6, s[4]);
            assertEquals((byte) 0x82, s[5]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadUnsignedShort() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            byte[] b = new byte[8];
            b[2] = (byte) 0xda;
            b[3] = (byte) 0x8e;
            b[4] = (byte) 0x2a;
            b[5] = (byte) 0x00;
            arr.set(0, b);
            assertEquals((short) 0x8eda, arr.getShort(0, 2));
            assertEquals((short) 0x2a, arr.getShort(0, 4));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteUnsignedShort() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 8);
            arr.putUnsignedShort(0, 2, 0x8eda);
            arr.putUnsignedShort(0, 4, 0x2a);
            byte[] s = new byte[8];
            arr.get(0, s);
            assertEquals((byte) 0xda, s[2]);
            assertEquals((byte) 0x8e, s[3]);
            assertEquals((byte) 0x2a, s[4]);
            assertEquals((byte) 0x00, s[5]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadInt() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 16);
            byte[] b = new byte[16];
            b[2] = (byte) 0xcd;
            b[3] = (byte) 0x86;
            b[4] = (byte) 0xf9;
            b[5] = (byte) 0x7f;
            b[6] = (byte) 0x32;
            b[7] = (byte) 0x79;
            b[8] = (byte) 0x06;
            b[9] = (byte) 0x80;
            arr.set(0, b);
            assertEquals(0x7ff986cd, arr.getInt(0, 2));
            assertEquals(0x80067932, arr.getInt(0, 6));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteInt() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 16);
            arr.putInt(0, 2, 0x7ff986cd);
            arr.putInt(0, 6, 0x80067932);
            byte[] s = new byte[16];
            arr.get(0, s);
            assertEquals((byte) 0xcd, s[2]);
            assertEquals((byte) 0x86, s[3]);
            assertEquals((byte) 0xf9, s[4]);
            assertEquals((byte) 0x7f, s[5]);
            assertEquals((byte) 0x32, s[6]);
            assertEquals((byte) 0x79, s[7]);
            assertEquals((byte) 0x06, s[8]);
            assertEquals((byte) 0x80, s[9]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadUnsignedInt() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 16);
            byte[] b = new byte[16];
            b[2] = (byte) 0xed;
            b[3] = (byte) 0xab;
            b[4] = (byte) 0xda;
            b[5] = (byte) 0xfe;
            b[6] = (byte) 0x2a;
            b[7] = (byte) 0x00;
            b[8] = (byte) 0x00;
            b[9] = (byte) 0x00;
            arr.set(0, b);
            assertEquals(0xfedaabedL, arr.getUnsignedInt(0, 2));
            assertEquals(0x2aL, arr.getUnsignedInt(0, 6));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteUnsignedInt() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 16);
            arr.putUnsignedInt(0, 2, 0xfedaabedL);
            arr.putUnsignedInt(0, 6, 0x2aL);
            byte[] s = new byte[16];
            arr.get(0, s);
            assertEquals((byte) 0xed, s[2]);
            assertEquals((byte) 0xab, s[3]);
            assertEquals((byte) 0xda, s[4]);
            assertEquals((byte) 0xfe, s[5]);
            assertEquals((byte) 0x2a, s[6]);
            assertEquals((byte) 0x00, s[7]);
            assertEquals((byte) 0x00, s[8]);
            assertEquals((byte) 0x00, s[9]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testReadLong() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 32);
            byte[] b = new byte[32];
            b[2] = (byte) 0x4d;
            b[3] = (byte) 0x36;
            b[4] = (byte) 0x0b;
            b[5] = (byte) 0xa2;
            b[6] = (byte) 0x89;
            b[7] = (byte) 0xed;
            b[8] = (byte) 0xf0;
            b[9] = (byte) 0x7f;
            b[10] = (byte) 0xb2;
            b[11] = (byte) 0xc9;
            b[12] = (byte) 0xf4;
            b[13] = (byte) 0x5d;
            b[14] = (byte) 0x76;
            b[15] = (byte) 0x12;
            b[16] = (byte) 0x0f;
            b[17] = (byte) 0x80;
            arr.set(0, b);
            assertEquals(0x7ff0ed89a20b364dL, arr.getLong(0, 2));
            assertEquals(0x800f12765df4c9b2L, arr.getLong(0, 10));
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testWriteLong() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(1, 32);
            arr.putLong(0, 2, 0x7ff0ed89a20b364dL);
            arr.putLong(0, 10, 0x800f12765df4c9b2L);
            byte[] s = new byte[32];
            arr.get(0, s);
            assertEquals((byte) 0x4d, s[2]);
            assertEquals((byte) 0x36, s[3]);
            assertEquals((byte) 0x0b, s[4]);
            assertEquals((byte) 0xa2, s[5]);
            assertEquals((byte) 0x89, s[6]);
            assertEquals((byte) 0xed, s[7]);
            assertEquals((byte) 0xf0, s[8]);
            assertEquals((byte) 0x7f, s[9]);
            assertEquals((byte) 0xb2, s[10]);
            assertEquals((byte) 0xc9, s[11]);
            assertEquals((byte) 0xf4, s[12]);
            assertEquals((byte) 0x5d, s[13]);
            assertEquals((byte) 0x76, s[14]);
            assertEquals((byte) 0x12, s[15]);
            assertEquals((byte) 0x0f, s[16]);
            assertEquals((byte) 0x80, s[17]);
        } finally {
            OffHeapUtils.free(arr);
        }
    }

    @Test
    public void testShrinkToFit() {
        OffHeapStructArrayList arr = null;
        try {
            arr = new OffHeapStructArrayList(8);
            byte[] buf = new byte[8];
            BT.putLong(buf, 0, 42);
            arr.add(buf);
            BT.putLong(buf, 0, 43);
            arr.add(buf);
            assertEquals(2, arr.size());
            assertTrue(arr.capacity() > 2);
            arr.shrinkToFit();
            assertEquals(2, arr.size());
            assertEquals(2, arr.capacity());
            arr.get(0, buf);
            assertEquals(42, BT.getLong(buf, 0));
            arr.get(1, buf);
            assertEquals(43, BT.getLong(buf, 0));
        } finally {
            OffHeapUtils.free(arr);
        }
    }
}
