package dev.tocraft.eomantle.client.book.data.content;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import dev.tocraft.eomantle.Mantle;
import dev.tocraft.eomantle.client.book.data.BookData;
import dev.tocraft.eomantle.client.book.data.element.ImageData;
import dev.tocraft.eomantle.client.screen.book.BookScreen;
import dev.tocraft.eomantle.client.screen.book.element.BookElement;
import dev.tocraft.eomantle.client.screen.book.element.ImageElement;

import java.util.ArrayList;

public class ContentImage extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("image");

  @Getter
  public String title = null;
  public ImageData image;

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y = getTitleHeight();

    if (this.title == null || this.title.isEmpty()) {
      y = 0;
    } else {
      this.addTitle(list, this.title);
    }

    if (this.image != null && this.image.location != null) {
      list.add(new ImageElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.image));
    } else {
      list.add(new ImageElement(ImageData.MISSING));
    }
  }
}
