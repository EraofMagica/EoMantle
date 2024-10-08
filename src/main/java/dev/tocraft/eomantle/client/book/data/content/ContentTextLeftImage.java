package dev.tocraft.eomantle.client.book.data.content;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import dev.tocraft.eomantle.Mantle;
import dev.tocraft.eomantle.client.book.data.BookData;
import dev.tocraft.eomantle.client.book.data.element.ImageData;
import dev.tocraft.eomantle.client.book.data.element.TextData;
import dev.tocraft.eomantle.client.screen.book.BookScreen;
import dev.tocraft.eomantle.client.screen.book.element.BookElement;
import dev.tocraft.eomantle.client.screen.book.element.ImageElement;
import dev.tocraft.eomantle.client.screen.book.element.TextElement;

import java.util.ArrayList;

public class ContentTextLeftImage extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("text_left_image");

  @Getter
  public String title = null;
  public ImageData image;
  public TextData[] text1;
  public TextData[] text2;

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y = getTitleHeight();

    if (this.title == null || this.title.isEmpty()) {
      y = 0;
    } else {
      this.addTitle(list, this.title);
    }

    if (this.image != null && this.image.location != null) {
      list.add(new ImageElement(0, y, 50, 50, this.image));
    } else {
      list.add(new ImageElement(0, y, 50, 50, ImageData.MISSING));
    }

    if (this.text1 != null && this.text1.length > 0) {
      list.add(new TextElement(55, y, BookScreen.PAGE_WIDTH - 55, 50, this.text1));
    }

    if (this.text2 != null && this.text2.length > 0) {
      list.add(new TextElement(0, y + 55, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - 55 - y, this.text2));
    }
  }
}
