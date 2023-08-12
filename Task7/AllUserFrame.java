package Task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AllUserFrame extends JFrame {
    private Map<Integer, User> userMap;
    private Map<Integer, List<Integer>> connectionMap;

    private Map<Integer, Integer> likesMap;
    private Map<Integer, Integer> messagesMap;
    private Map<Integer, Integer> commentsMap;
    private JPanel userPanel;

    public AllUserFrame() {
        setTitle("All Users");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        userMap = new HashMap<>();
        connectionMap = new HashMap<>();
        likesMap = new HashMap<>();
        messagesMap = new HashMap<>();
        commentsMap = new HashMap<>();

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create user panel
        userPanel = createUserPanel();
        mainPanel.add(userPanel, BorderLayout.CENTER);

        // Load user details from file and create user components
        loadUserDetails();
        loadConnectionDetails();

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JPanel createUserPanel() {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return userPanel;
    }

    private void loadUserDetails() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Task7/user_details.txt"));

            String line;
            int gridy = 0;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails.length == 7) {
                    int userId = Integer.parseInt(userDetails[0]);
                    String userName = userDetails[1];
                    String age = userDetails[2];
                    String location = userDetails[3];
                    String followers = userDetails[4];
                    String imagePath = userDetails[5];
                    String details = userDetails[6];

                    User user = new User(userId, userName, age, location, followers, imagePath, details);
                    userMap.put(userId, user);

                    JPanel userCard = createUserCard(user);
                    userPanel.add(userCard);

                    gridy++;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadConnectionDetails() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Task7/connection_details.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] connectionDetails = line.split(":");
                if (connectionDetails.length == 5) {
                    int userId = Integer.parseInt(connectionDetails[0]);
                    int connectedTo = Integer.parseInt(connectionDetails[1]);
                    int numMessages = Integer.parseInt(connectionDetails[2]);
                    int numLikes = Integer.parseInt(connectionDetails[3]);
                    int numComments = Integer.parseInt(connectionDetails[4]);

                    // Add the connection from userId to connectedTo
                    if (connectionMap.containsKey(userId)) {
                        List<Integer> userConnections = connectionMap.get(userId);
                        userConnections.add(connectedTo);
                        connectionMap.put(userId, userConnections);
                    } else {
                        List<Integer> userConnections = new ArrayList<>();
                        userConnections.add(connectedTo);
                        connectionMap.put(userId, userConnections);
                    }

                    // Add the connection from connectedTo to userId
                    if (connectionMap.containsKey(connectedTo)) {
                        List<Integer> userConnections = connectionMap.get(connectedTo);
                        userConnections.add(userId);
                        connectionMap.put(connectedTo, userConnections);
                    } else {
                        List<Integer> userConnections = new ArrayList<>();
                        userConnections.add(userId);
                        connectionMap.put(connectedTo, userConnections);
                    }

                    likesMap.put(connectedTo, numLikes);
                    messagesMap.put(connectedTo, numMessages);
                    commentsMap.put(connectedTo, numComments);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading connection details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createUserCard(User user) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.setPreferredSize(new Dimension(300, 120));

        ImageIcon userImage = new ImageIcon(user.getImagePath());
        Image scaledImage = userImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledUserImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledUserImage);
        cardPanel.add(imageLabel, BorderLayout.WEST);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name: " + user.getUserName());
        detailsPanel.add(nameLabel);

        JLabel ageLabel = new JLabel("Age: " + user.getAge());
        detailsPanel.add(ageLabel);

        JLabel locationLabel = new JLabel("Location: " + user.getLocation());
        detailsPanel.add(locationLabel);

        JLabel followersLabel = new JLabel("Followers: " + user.getFollowers());
        detailsPanel.add(followersLabel);

        cardPanel.add(detailsPanel, BorderLayout.CENTER);

        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDetailFrame frame = new UserDetailFrame(user, connectionMap, userMap,
                        likesMap, messagesMap, commentsMap);
                frame.setVisible(true);
            }
        });
        cardPanel.add(viewButton, BorderLayout.SOUTH);

        return cardPanel;
    }

    private void showUserListDialog(List<User> users, String searchTerm) {
        JDialog dialog = new JDialog(this, "Matching Users", true);
        dialog.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Matching Users for: " + searchTerm);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(titleLabel, BorderLayout.NORTH);

        JPanel usersPanel = new JPanel(new GridLayout(users.size(), 1, 10, 10));
        usersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (User user : users) {
            JPanel userCard = createUserCard(user);
            usersPanel.add(userCard);
        }

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton viewButton = new JButton("View Selected Users");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> selectedUsers = new ArrayList<>();
                Component[] userCards = usersPanel.getComponents();
                for (Component card : userCards) {
                    if (card instanceof JPanel) {
                        JPanel userCard = (JPanel) card;
                        JButton viewDetailsButton = (JButton) userCard.getComponent(1);
                        if (viewDetailsButton.isSelected()) {
                            User selectedUser = userMap.get(viewDetailsButton.getClientProperty("userId"));
                            selectedUsers.add(selectedUser);
                        }
                    }
                }

                if (!selectedUsers.isEmpty()) {
                    for (User selectedUser : selectedUsers) {
                        UserDetailFrame frame = new UserDetailFrame(selectedUser, connectionMap, userMap,
                                likesMap, messagesMap, commentsMap);
                        frame.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please select at least one user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AllUserFrame frame = new AllUserFrame(); // this is the main frame that contains all the users
            frame.setVisible(true);
        });
    }

}
