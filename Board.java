import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Board
{
    // Array to hold board cards
    private FlippableCard cards[];

    // Resource loader
    private ClassLoader loader = getClass().getClassLoader();

    public Board(int size, ActionListener AL)
    {
        // Allocate and configure the game board: an array of cards
        cards = new FlippableCard[size];

        // Fill the Cards array
        int imageIdx = 1;
        for (int i = 0; i < size; i++) {

            // Load the front image from the resources folder
            String imgPath = "res/hub" + imageIdx + ".jpg";
            ImageIcon img = new ImageIcon(loader.getResource(imgPath));

            if(i % 2 != 0){ //We only want two cards to have the same image, so change the index on every odd i
                imageIdx++;  // get ready for the next pair of cards
            }

            // Setup one card at a time
            FlippableCard c = new FlippableCard(img);
            c.addActionListener(AL);

            // Add them to the array
            cards[i] = c;
            cards[i].setCustomName(imgPath);
        }

        // use random numb generator to assist in randomizing the array
        Random numb = new Random();
        for (int i = 0; i < cards.length; i++) {
            int rand = numb.nextInt(cards.length);
            FlippableCard temp = cards[i];
            cards[i] = cards[rand];
            cards[rand] = temp;
        }


    }

    public void fillBoardView(JPanel view)
    {
        for (FlippableCard c : cards) {

            view.add(c);
        }
    }

    public void resetBoard()
    {
        // reset flipped cards, randomize the cards
        // used random numb calculator to assist in randomizing
        // the array
        for (int i = 0; i < cards.length; i++) {
            cards[i].hideFront();
        }

        Random numb = new Random();
        for (int i = 0; i < cards.length; i++) {
            int rand = numb.nextInt(cards.length);
            FlippableCard temp = cards[i];
            cards[i] = cards[rand];
            cards[rand] = temp;
        }


    }
}
