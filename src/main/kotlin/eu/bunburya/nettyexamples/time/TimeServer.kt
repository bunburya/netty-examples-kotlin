package eu.bunburya.nettyexamples.time

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

/**
 * Returns the time as a 32-bit integer.
 */
class TimeServer(private val port: Int) {

    @Throws(Exception::class)
    fun run() {
        val bossGroup: EventLoopGroup = NioEventLoopGroup()
        val workerGroup: EventLoopGroup = NioEventLoopGroup()
        try {
            val b: ServerBootstrap = ServerBootstrap().apply {
                group(bossGroup, workerGroup)
                channel(NioServerSocketChannel::class.java)
                childHandler(object: ChannelInitializer<SocketChannel>() {
                    @Throws(Exception::class)
                    override fun initChannel(ch: SocketChannel) {
                        ch.pipeline().addLast(TimeEncoder(), TimeServerHandler())
                    }
                })
                option(ChannelOption.SO_BACKLOG, 128)
                childOption(ChannelOption.SO_KEEPALIVE, true)
            }

            // Bind and start to accept incoming connections.
            val f: ChannelFuture = b.bind(port).sync()

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully shut down your server.
            f.channel().closeFuture().sync()
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }
}

@Throws(Exception::class)
fun main(args: Array<String>) {
    val port: Int = if (args.isNotEmpty()) args[0].toInt() else 8080
    TimeServer(port).run()
}