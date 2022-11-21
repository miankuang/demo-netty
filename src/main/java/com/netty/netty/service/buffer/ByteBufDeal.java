package com.netty.netty.service.buffer;

import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import java.nio.charset.StandardCharsets;

/**
 * @author ytgaom
 * @date 2022/11/17 11:35
 */
public class ByteBufDeal {

    public static void main(String[] args) {
        //创建堆缓冲区
        ByteBuf buf1 = Unpooled.buffer(1);
        int capacity = buf1.capacity();
        buf1.writeBytes("你".getBytes(StandardCharsets.UTF_8));
        int i = buf1.readableBytes();  //可读字节
        boolean b = buf1.hasArray();   //底层数据存储是否数组实现（堆缓冲区）
        int i2 = buf1.refCnt();        //对象的引用数量
        boolean direct1 = buf1.isDirect(); //是否直接存储

        //创建直接缓存取
        ByteBuf buf2 = Unpooled.directBuffer(1);
        buf2.writeBytes("好".getBytes(StandardCharsets.UTF_8));
        int i1 = buf2.readableBytes();
        boolean b1 = buf2.hasArray();
        boolean direct = buf2.isDirect();

        //将buf2写入到buf1
        buf1.writeBytes(buf2);
        System.out.println(StrUtil.format("after buf1 write buf2, buf1 is:{}, buf2.readableBytes:{}", buf1.toString(StandardCharsets.UTF_8), buf2.readableBytes()));
        int i3 = buf1.indexOf(3, buf1.capacity(), "好".getBytes()[1]);
        byte[] buf1Byte = new byte[buf1.readableBytes()/2];
        buf1.getBytes(0, buf1Byte);
        int i4 = buf1.forEachByte(k -> k != "好".getBytes()[1]);

        //创建直接缓存取
        ByteBuf buf3 = Unpooled.directBuffer(1);
        buf3.writeBytes(" My".getBytes(StandardCharsets.UTF_8));
        ByteBuf wrappedBuffer1 = Unpooled.wrappedBuffer(buf1, buf3);
        System.out.println("wrappedBuffer1:" + wrappedBuffer1.toString(StandardCharsets.UTF_8));

        ByteBuf buf4 = buf3.duplicate();  //复杂一个buffer，复制的数据，索引独立，内容共享
        System.out.println("duplicate buf4:" + buf4.toString(StandardCharsets.UTF_8));
        buf3.writeBytes(" world".getBytes(StandardCharsets.UTF_8));
        buf4.writerIndex(9);
        System.out.println("duplicate buf4:" + buf4.toString(StandardCharsets.UTF_8));
        ByteBuf wrappedBuffer2 = Unpooled.wrappedBuffer(buf1, buf3);
        System.out.println("wrappedBuffer1:" + wrappedBuffer1.toString(StandardCharsets.UTF_8));
        System.out.println("wrappedBuffer2:" + wrappedBuffer2.toString(StandardCharsets.UTF_8));
        CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer(2);
        compositeBuffer.addComponents(buf1, buf2);
        for (ByteBuf buf : compositeBuffer) {
            System.out.print("compositeBuffer:" + buf.toString(StandardCharsets.UTF_8) + " ");
        }
        System.out.println();
        //mark 与 reset搭配
        ByteBuf buf5 = Unpooled.buffer(32);
        buf5.writeBytes("已读 ".getBytes(StandardCharsets.UTF_8));
        buf5.markReaderIndex();    // 当前读指针指向:0
        buf5.writeBytes("正文".getBytes(StandardCharsets.UTF_8));
        buf5.markWriterIndex();   //  当前写指针指向:正文后一位
        buf5.writeBytes(" 已写".getBytes(StandardCharsets.UTF_8));
        System.out.println("source buf5:" + buf5.toString(StandardCharsets.UTF_8));
        ByteBuf buf = buf5.readBytes(buf5.readableBytes());
        System.out.println("after read buf5:" + buf5.toString(StandardCharsets.UTF_8));
        buf5.resetReaderIndex();
        System.out.println("after read reset buf5:" + buf5.toString(StandardCharsets.UTF_8));
        buf5.resetWriterIndex();
        System.out.println("after write reset buf5:" + buf5.toString(StandardCharsets.UTF_8));
        System.out.println("success");
    }

}
