package eu.bunburya.nettyexamples.discard

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil

/**
 * Handles a server-side channel.
 */
class DiscardServerHandler: ChannelInboundHandlerAdapter() {

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val input: ByteBuf = msg as ByteBuf
        try {
            println(input.toString(CharsetUtil.US_ASCII))
        } finally {
            input.release()
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        // Close the connection when an exception is raised.
        cause.printStackTrace()
        ctx.close()
    }

}