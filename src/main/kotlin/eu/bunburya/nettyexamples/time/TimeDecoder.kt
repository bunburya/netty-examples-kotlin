package eu.bunburya.nettyexamples.time

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class TimeDecoder: ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, input: ByteBuf, output: MutableList<Any>) {
        if (input.readableBytes() < 4) return
        output.add(UnixTime(input.readUnsignedInt()))
    }

}