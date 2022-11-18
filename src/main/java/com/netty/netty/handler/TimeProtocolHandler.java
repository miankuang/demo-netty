package com.netty.netty.handler;

import cn.hutool.core.date.LocalDateTimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.util.AntPathMatcher;

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

    String lineSeparator = System.getProperty("line.separator");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer(32);
        String response = "连接成功时间:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss") + lineSeparator;
        buffer.writeBytes(response.getBytes(Charset.forName("GBK")));
        ctx.writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            ByteBuf buf = (ByteBuf) msg;
            ByteBuf buffer = ctx.alloc().buffer(32);
            String inputStr = buf.toString(Charset.forName("GBK"));
            buffer.writeBytes(inputStr.getBytes(Charset.forName("GBK")));
            if (inputStr.equals("a")) {
                String response = lineSeparator + "当前时间:" + LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss");
                buffer.writeBytes(response.getBytes(Charset.forName("GBK")));
            }
            buffer.writeBytes(lineSeparator.getBytes(Charset.forName("GBK")));
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
