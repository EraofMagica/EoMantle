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

public class ContentImageText extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("image_text");

  @Getter
  public String title = null;
  public ImageData image;
  public TextData[] text;
  public boolean centerImage = true;

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y = getTitleHeight();

    if (this.title == null || this.title.isEmpty()) {
      y = 0;
    } else {
      this.addTitle(list, this.title);
    }

    if (this.image != null && this.image.location != null) {
      int x = 0;
      int width = BookScreen.PAGE_WIDTH;
      if (centerImage && this.image.width != -1) {
        x = (BookScreen.PAGE_WIDTH - this.image.width) / 2;
        width = this.image.width;
      }
      ImageElement element = new ImageElement(x, y, width, 100, this.image);
      list.add(element);
      y += element.height + 5;
    } else {
      list.add(new ImageElement(0, y, 32, 32, ImageData.MISSING));
      y += 37;
    }

    if (this.text != null && this.text.length > 0) {
      list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.text));
    }
  }
}
