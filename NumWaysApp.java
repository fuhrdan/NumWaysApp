import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NumWaysApp extends JFrame {

    private JTextField wordsInputField;
    private JTextField targetInputField;
    private JTextArea outputArea;

    public NumWaysApp() {
        setTitle("Num Ways Calculator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Words (comma separated):"));
        wordsInputField = new JTextField();
        inputPanel.add(wordsInputField);

        inputPanel.add(new JLabel("Target:"));
        targetInputField = new JTextField();
        inputPanel.add(targetInputField);

        JButton calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        // Output panel
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Action listener for button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateNumWays();
            }
        });
    }

    private void calculateNumWays() {
        // Read inputs
        String wordsInput = wordsInputField.getText().trim();
        String target = targetInputField.getText().trim();

        if (wordsInput.isEmpty() || target.isEmpty()) {
            outputArea.setText("Please enter both words and a target.");
            return;
        }

        String[] words = wordsInput.split(",");
        int ways = numWays(words, target);

        outputArea.setText("Number of ways: " + ways + "\n\nDetails:\n");
        outputArea.append(getWaysDetails(words, target));
    }

private int numWays(String[] words, String target) {
    int m = target.length();
    int n = words[0].length();

    // Validate inputs
    if (!isValidInput(words, target)) {
        outputArea.setText("Error: Words and target must only contain lowercase English letters.");
        return 0;
    }

    int[][] cnt = new int[n][26];
    for (String word : words) {
        for (int j = 0; j < n; ++j) {
            cnt[j][word.charAt(j) - 'a']++;
        }
    }

    Integer[][] dp = new Integer[m][n];
    return dfs(0, 0, m, n, target, cnt, dp);
}

private boolean isValidInput(String[] words, String target) {
    for (String word : words) {
        if (!word.matches("[a-z]+")) {
            return false;
        }
    }
    return target.matches("[a-z]+");
}

    private int dfs(int i, int j, int m, int n, String target, int[][] cnt, Integer[][] dp) {
        if (i >= m) {
            return 1;
        }
        if (j >= n) {
            return 0;
        }
        if (dp[i][j] != null) {
            return dp[i][j];
        }

        long ans = dfs(i, j + 1, m, n, target, cnt, dp);
        if (cnt[j][target.charAt(i) - 'a'] > 0) {
            ans += 1L * dfs(i + 1, j + 1, m, n, target, cnt, dp) * cnt[j][target.charAt(i) - 'a'];
            ans %= (int) 1e9 + 7;
        }
        return dp[i][j] = (int) ans;
    }

    private String getWaysDetails(String[] words, String target) {
        // Placeholder for actual ways details logic
        // In a real implementation, this function would compute and list all possible combinations
        return "Currently, this application computes the number of ways but not the detailed combinations.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumWaysApp app = new NumWaysApp();
            app.setVisible(true);
        });
    }
}
