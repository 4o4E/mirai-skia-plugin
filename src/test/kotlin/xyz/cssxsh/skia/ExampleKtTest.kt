package xyz.cssxsh.skia

import kotlinx.coroutines.*
import org.jetbrains.skia.*
import org.jetbrains.skia.svg.*
import org.jetbrains.skiko.*
import org.junit.jupiter.api.*
import xyz.cssxsh.mirai.skia.*
import java.io.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ExampleKtTest {

    private val fonts = File("./run/fonts")

    @BeforeAll
    fun init() {
        fonts.mkdirs()
        System.setProperty("xyz.cssxsh.mirai.gif.release", "https://github.com/cssxsh/gif-jni")
        val links = arrayOf(
            "https://forum.freemdict.com/uploads/short-url/57rcFi1dOBZBbcQu6762Y776rVD.ttf",
            "https://cdn.cnbj1.fds.api.mi-img.com/vipmlmodel/font/MiSans/MiSans.zip",
            "https://github.com/googlefonts/noto-emoji/archive/refs/tags/v2.038.tar.gz",
            "https://github.com/be5invis/Sarasa-Gothic/releases/download/v0.37.4/sarasa-gothic-ttf-0.37.4.7z"
        )
        runBlocking {
            loadJNILibrary(folder = File("./run/lib"))
            if (fonts.list().isNullOrEmpty()) downloadTypeface(folder = fonts, links = links)
        }
    }

    @Test
    fun font() {
        println(FontUtils.families())

        when (hostOs) {
            OS.Windows -> {
                Assertions.assertNotNull(FontUtils.matchSimSun(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchNSimSun(FontStyle.NORMAL))
//                Assertions.assertNotNull(FontUtils.matchSimHei(FontStyle.NORMAL))
//                Assertions.assertNotNull(FontUtils.matchFangSong(FontStyle.NORMAL))
//                Assertions.assertNotNull(FontUtils.matchKaiTi(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchArial(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchCalibri(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchConsolas(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchTimesNewRoman(FontStyle.NORMAL))
            }
            OS.Linux -> {
                Assertions.assertNotNull(FontUtils.matchLiberationSans(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchLiberationSerif(FontStyle.NORMAL))
            }
            OS.MacOS -> {
                Assertions.assertNotNull(FontUtils.matchArial(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchTimesNewRoman(FontStyle.NORMAL))
                Assertions.assertNotNull(FontUtils.matchHelvetica(FontStyle.NORMAL))
            }
            else -> Unit
        }

        loadTypeface(folder = fonts)
        println(FontUtils.provider.makeFamilies())
    }

    @Test
    fun svg() {
        val surface = Surface.makeRasterN32Premul(350, 350)
        val size = Point(350F, 350F)
        val background = SVGDOM.makeFromFile(xml = File("./example/osu-logo-triangles.svg"))
        background.setContainerSize(size)
        background.render(surface.canvas)
        val text = SVGDOM.makeFromFile(xml = File("./example/osu-logo-white.svg"))
        text.setContainerSize(size)
        text.render(surface.canvas)

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/Osu.png")
        file.writeBytes(data.bytes)
    }

    @Test
    fun stats() {
        val stats = SVGDOM.makeFromFile(xml = File("./example/cssxsh.svg"))
        val surface = Surface.makeRasterN32Premul(stats.root!!.width.value.toInt(), stats.root!!.height.value.toInt())

        stats.setContainerSize(stats.root!!.width.value, stats.root!!.height.value)
        stats.render(surface.canvas)

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/cssxsh.png")
        file.writeBytes(data.bytes)
    }

    @Test
    fun pornhub() {
        val surface = pornhub("Git", "Hub")

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/pornhub.png")
        file.writeBytes(data.bytes)
    }

    @Test
    fun petpet() {
        System.setProperty(PET_PET_SPRITE, "./example/sprite.png")
        val data = petpet(face = Image.makeFromEncoded(File("./example/face.png").readBytes()))

        val file = File("./run/petpet.gif")
        file.writeBytes(data.bytes)
    }

    @Test
    fun choyen() {
        val surface = choyen(top = "好了", bottom = "够意思")

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/choyen.png")
        file.writeBytes(data.bytes)
    }

    @Test
    fun zzkia() {
        System.setProperty(ZZKIA_ORIGIN, "./example/zzkia.jpg")
        val surface = zzkia("有内鬼，停止交易")

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/zzkia.png")
        file.writeBytes(data.bytes)
    }

    @Test
    fun tank() {
        val top = Image.makeFromEncoded(File("./example/90446978_p0.png").readBytes())
        val bottom = Image.makeFromEncoded(File("./example/90487779_p0.png").readBytes())
        val surface = tank(top = top, bottom = bottom)

        val image = surface.makeImageSnapshot()
        val data = image.encodeToData() ?: throw IllegalStateException("encode null.")

        val file = File("./run/tank.png")
        file.writeBytes(data.bytes)
    }
}