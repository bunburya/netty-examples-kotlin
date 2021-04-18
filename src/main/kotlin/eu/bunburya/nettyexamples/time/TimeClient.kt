package eu.bunburya.nettyexamples.time

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import kotlin.jvm.Throws

@Throws(Exception::class)
fun main(args: Array<String>) {
    val host: String = args[0]
    val port: Int = args[1].toInt()
    val workerGroup: EventLoopGroup = NioEventLoopGroup()

    try {
        val b: Bootstrap = Bootstrap().apply {
            group(workerGroup)
            channel(NioSocketChannel::class.java)
            option(ChannelOption.SO_KEEPALIVE, true)
            handler(object: ChannelInitializer<SocketChannel>() {
                @Throws(Exception::class)
                override fun initChannel(ch: SocketChannel) {
                    ch.pipeline().addLast(TimeDecoder(), TimeClientHandler())
                }
            })
        }
        // Start the client.
        val f: ChannelFuture = b.connect(host, port).sync()

        // Wait until the connection is closed.
        f.channel().closeFuture().sync()
    } finally {
        workerGroup.shutdownGracefully()
    }
}