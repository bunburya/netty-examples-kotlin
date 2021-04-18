package eu.bunburya.nettyexamples.time

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class TimeEncoder: MessageToByteEncoder<UnixTime>() {

    override fun encode(ctx: ChannelHandlerContext, msg: UnixTime, output: ByteBuf) {
        output.writeInt(msg.value.toInt())
    }

}