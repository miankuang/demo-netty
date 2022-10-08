package com.netty.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 输入响应协议
 * @author ytgaom
 * @date 2022/10/8 14:47
 */
public class EchoProtocolHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("【EchoProtocolHandler#channelRead】收到一个消息：" + ((ByteBuf) msg).toString(Charset.forName("GB2312")));
        ctx.writeAndFlush(msg);
    }
}
