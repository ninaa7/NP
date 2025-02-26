package Kolokviumski2;

import java.util.*;

class Comment implements Comparable<Comment>{
    String username;
    String commentId;
    String content;
    //String replyToId;
    ArrayList<Comment> comments;
    int likes;

    public Comment(String username, String commentId, String content) {
        this.username = username;
        this.commentId = commentId;
        this.content = content;
        comments = new ArrayList<>();
        likes = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public Comment findComment (String id) {
        if (commentId.equals(id)) {
            return this;
        }

        for (Comment comment : comments) {
            Comment found = comment.findComment(id);
            if (found!=null) {
                return found;
            }
        }
        return null;
    }

    public void likeComment() {
        likes++;
    }

    public void addComment (Comment comment) {
        comments.add(comment);
    }

    public int getTotalLikes () {
        int total = likes;
        for (Comment comment : comments) {
            total+=comment.getTotalLikes();
        }
        return total;
    }

    public String print (int level) {
        StringBuilder sb = new StringBuilder();
        String tab = "    " + "    ".repeat(level);

        sb.append(tab).append("Comment: ").append(content).append("\n");
        sb.append(tab).append("Written by: ").append(username).append("\n");
        sb.append(tab).append("Likes: ").append(likes).append("\n");
        comments.stream().sorted(Comparator.reverseOrder()).forEach(x->sb.append(x.print(level+1)));
        return sb.toString();
    }

    @Override
    public int compareTo(Comment o) {
        return Integer.compare(this.getTotalLikes(), o.getTotalLikes());
    }
}

class Post {
    String username;
    String postContent;
    ArrayList<Comment> comments;

    public Post(String username, String postContent) {
        this.username = username;
        this.postContent = postContent;
        this.comments = new ArrayList<>();
    }

    public Comment findComment (String id) {
        for (Comment comment : comments) {
            Comment found = comment.findComment(id);
            if (found!=null) {
                return found;
            }
        }
        return null;
    }

    public void addComment(String username, String id, String content, String replyToId) {
        Comment comment = new Comment(username,id,content);

        if(replyToId == null) {
            comments.add(comment);
            return;
        }

        Comment target = findComment(replyToId);
        if (target!=null) {
            target.addComment(comment);
        }
    }

    public void likeComment(String id) {
        Comment commentToLike = findComment(id);
        if (commentToLike != null) {
            commentToLike.likeComment();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Post: ").append(postContent).append("\n");
        sb.append("Written by: ").append(username).append("\n");
        sb.append("Comments:\n");

        comments.stream().sorted(Comparator.reverseOrder()).forEach(x->sb.append(x.print(1)));
        return sb.toString();
    }
}


public class PostTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String postAuthor = sc.nextLine();
        String postContent = sc.nextLine();

        Post p = new Post(postAuthor, postContent);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String testCase = parts[0];

            if (testCase.equals("addComment")) {
                String author = parts[1];
                String id = parts[2];
                String content = parts[3];
                String replyToId = null;
                if (parts.length == 5) {
                    replyToId = parts[4];
                }
                p.addComment(author, id, content, replyToId);
            } else if (testCase.equals("likes")) { //likes;1;2;3;4;1;1;1;1;1 example
                for (int i = 1; i < parts.length; i++) {
                    p.likeComment(parts[i]);
                }
            } else {
                System.out.println(p);
            }

        }
    }
}

