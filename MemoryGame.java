import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// used the skeleton Proj0 that was provided on Canvas

public class MemoryGame extends JFrame implements ActionListener
{
    // Core game play objects
    private Board gameBoard;
    private FlippableCard prevCard1, prevCard2;

    // Labels to display game info
    private JLabel matchesMade, guessesMade;

    // layout objects: Views of the board and the label area
    private JPanel boardView, labelView;

    // Record keeping counts and times
    private int clickCount = 0, gameTime = 0, guesses = 0;
    private int pairsFound = 0;

    // Game timer: will be configured to trigger an event every second
    private Timer gameTimer;

    // have a TimerListener class to handle when timer is pressed
    // will only fire when the second card in a pair is pressed
    // will pause for one second after both are selected then begin
    // accepting user input again
    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            prevCard2.showFront();

            // if they are same icon, then it is a match
            // otherwise flip over
            // remove the listener so it will stay flipped over even when clicked again
            // update the JLabel at the end. If not a match then flip them back over
            if (prevCard1.customName().equals(prevCard2.customName())) {
                prevCard1.removeActionListener(this);
                prevCard2.removeActionListener(this);
                pairsFound++;
                matchesMade.setText("Matches: " + pairsFound);


            } else {
                prevCard1.hideFront();
                prevCard2.hideFront();

            }
            guesses++;
            guessesMade.setText("Guesses: " + guesses);
            gameTimer.stop();
        }
    }

    public MemoryGame()
    {

        // Call the base class constructor
        super("Hubble Memory Game");

        // declare timer
        gameTimer = new Timer(1000, new TimerListener());


        // Allocate the interface elements
        JButton restart = new JButton("Restart");
        JButton quit = new JButton("Quit");
        guessesMade = new JLabel("Guesses: " + guesses);
        matchesMade = new JLabel("Matches: " + pairsFound);

        // add separate listeners for the restart and quit button
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        // Allocate two major panels to hold interface
        labelView = new JPanel();  // used to hold labels
        boardView = new JPanel();  // used to hold game board

        // get the content pane, onto which everything is eventually added
        Container c = getContentPane();

        // Setup the game board with cards
        gameBoard = new Board(25, this);

        // Add the game board to the board layout area
        boardView.setLayout(new GridLayout(5, 5, 2, 0));
        gameBoard.fillBoardView(boardView);

        // Add required interface elements to the "label" JPanel
        labelView.setLayout(new GridLayout(1, 4, 2, 2));
        labelView.add(quit);
        labelView.add(restart);
        labelView.add(guessesMade);
        labelView.add(matchesMade);

        // Both panels should now be individually layed out
        // Add both panels to the container
        c.add(labelView, BorderLayout.NORTH);
        c.add(boardView, BorderLayout.SOUTH);

        setSize(745, 500);
        setVisible(true);
    }


    /* Handle anything that gets clicked and that uses MemoryGame as an
     * ActionListener */
    public void actionPerformed(ActionEvent e)
    {
        // Get the currently clicked card from a click event
        FlippableCard currCard = (FlippableCard)e.getSource();

        // begin by dealing with when the smiley is clicked
        // smiley click doesn't count as an actual turn, just show
        // the front for the rest of the game
        if ("res/hub13.jpg".equals(currCard.customName())) {
            currCard.showFront();
            currCard.removeActionListener(this);
            return;
        }

        // don't accept user input if the timer is currently running
        // this keeps the user from flipping over more than two cards
        if (gameTimer.isRunning())
            return;

        // show card icon
        clickCount++;
        currCard.showFront();

        // if the click is odd, assign the current card to
        // previous card one. If even, assign to prev card two.
        // We do this so we can compare the two previous cards later.
        // If user clicks on exact same icon, ignore and don't increment clickCount
        // start timer at the end
        if (clickCount % 2 != 0) {
            prevCard1 = currCard;

        } else {
            prevCard2 = currCard;
            if (prevCard1 == prevCard2) {
                clickCount--;
                return;
            }
            gameTimer.start();

        }

    }

    // reset everything back to 0 and reset JLabels
    private void restartGame()
    {
        pairsFound = 0;
        gameTime = 0;
        clickCount = 0;
        guesses = 0;
        guessesMade.setText("Guesses: " + guesses);
        matchesMade.setText("Matches: " + pairsFound);

        // Clear the boardView and have the gameBoard generate a new layout

        gameBoard.resetBoard();
        gameBoard.fillBoardView(boardView);
    }

    // main function to begin play
    public static void main(String args[])
    {
        MemoryGame M = new MemoryGame();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
