package com.netty.netty.service.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author ytgaom
 * @date 2022/11/17 11:35
 */
public class ByteBufDeal {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(1);
        int capacity = buffer.capacity();
        buffer.writeBytes("你".getBytes(StandardCharsets.UTF_8));
        int i = buffer.readableBytes();
        boolean b = buffer.hasArray();

        ByteBuf byteBuf = Unpooled.directBuffer(1);
        byteBuf.writeBytes("好".getBytes(StandardCharsets.UTF_8));
        int i1 = byteBuf.readableBytes();
        boolean b1 = byteBuf.hasArray();

        boolean direct1 = buffer.isDirect();
        boolean direct = byteBuf.isDirect();

        ByteBuf byteBuf1 = Unpooled.wrappedBuffer(buffer, byteBuf);
        System.out.println("wrappedBuffer1:" + byteBuf1.toString(StandardCharsets.UTF_8));

        buffer.writeBytes("hello".getBytes(StandardCharsets.UTF_8));
        byteBuf.writeBytes(" world".getBytes(StandardCharsets.UTF_8));

        int i2 = buffer.refCnt();

        ByteBuf wrappedBuffer2 = Unpooled.wrappedBuffer(buffer, byteBuf);
        System.out.println("wrappedBuffer1:" + byteBuf1.toString(StandardCharsets.UTF_8));
        System.out.println("wrappedBuffer2:" + wrappedBuffer2.toString(StandardCharsets.UTF_8));
        byteBuf.resetReaderIndex();
        CompositeByteBuf compositeBuffer = Unpooled.compositeBuffer(2);
        compositeBuffer.addComponents(buffer, byteBuf);
        System.out.println("compositeBuffer:" + compositeBuffer.toString(StandardCharsets.UTF_8));


        System.out.println("success");
    }

}
