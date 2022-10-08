package com.netty.netty.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 时间协议
 * @author ytgaom
 * @date 2022/10/8 15:02
 */
public class TimeProtocolHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer(32);
        String response = "连接成功时间:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss") + "\r\n";
        buffer.writeBytes(response.getBytes(Charset.forName("GB2312")));
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            ByteBuf buf = (ByteBuf) msg;
            ByteBuf buffer = ctx.alloc().buffer(32);
            String inputStr = buf.toString(Charset.forName("GB2312"));
            buffer.writeBytes(inputStr.getBytes(Charset.forName("GB2312")));
            if (inputStr.equals("a")) {
                String response = "\r\n当前时间:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
                buffer.writeBytes(response.getBytes(Charset.forName("GB2312")));
            }
            buffer.writeBytes("\r\n".getBytes(Charset.forName("GB2312")));
            ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
