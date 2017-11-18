package cc.aoeiuv020.supersonicspeedcar

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.Viewport

/**
 *
 * Created by AoEiuV020 on 2017.11.18-19:31:51.
 */
class Background(private val texture: Texture) : Actor() {
    private lateinit var images: List<Image>

    override fun draw(batch: Batch?, parentAlpha: Float) {
        images.forEach { image ->
            image.draw(batch, parentAlpha)
        }
    }

    fun update(camera: Camera) {
        while (images[0].y + images[0].height < camera.position.y - camera.viewportHeight / 2) {
            images.forEach { image ->
                image.y += image.height
            }
        }
    }

    fun init(viewport: Viewport) {
        val tmpImages = mutableListOf<Image>()
        val width = viewport.screenWidth.toFloat()
        val height = viewport.screenHeight.toFloat() / viewport.screenWidth * width
        val x = 0f
        var y = 0f
        while (tmpImages.size * height < height + viewport.screenHeight) {
            val image = Image(texture)
            image.width = width
            image.height = height
            image.x = x
            image.y = y
            y += height
            tmpImages.add(image)
        }
        images = tmpImages
    }
}