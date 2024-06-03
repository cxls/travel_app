package com.hjq.demo.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 协同过滤算法匹配旅游搭子
 * @author flight
 * @date 2024/6/3
 */
public class TravelBuddyMatcher {
    // 用户实体类
    static class User {
        int userId;              // 用户ID
        int age;                 // 用户年龄
        String profession;       // 用户职业
        List<String> favoriteSpots; // 用户常去的景点列表

        User(int userId, int age, String profession, List<String> favoriteSpots) {
            this.userId = userId;
            this.age = age;
            this.profession = profession;
            this.favoriteSpots = favoriteSpots;
        }
    }

    // 匹配结果实体类
    static class Match {
        User user1;     // 匹配的第一个用户
        User user2;     // 匹配的第二个用户
        double score;   // 匹配得分

        Match(User user1, User user2, double score) {
            this.user1 = user1;
            this.user2 = user2;
            this.score = score;
        }
    }

    public static void main(String[] args) {
        // 示例用户数据
        List<User> users = Arrays.asList(
                new User(1, 25, "Engineer", Arrays.asList("Beach", "Museum")),
                new User(2, 30, "Teacher", Arrays.asList("Mountain", "Museum")),
                new User(3, 28, "Engineer", Arrays.asList("Beach", "Mountain")),
                new User(4, 35, "Doctor", Arrays.asList("Beach", "Park"))
        );

        // 查找匹配用户
        List<Match> matches = findMatches(users);
        for (Match match : matches) {
            System.out.printf("User %d and User %d have a match score of %.2f\n", match.user1.userId, match.user2.userId, match.score);
        }
    }

    /**
     * 查找匹配用户的方法
     * @param users 用户列表
     * @return 匹配结果列表
     */
    private static List<Match> findMatches(List<User> users) {
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            for (int j = i + 1; j < users.size(); j++) {
                // 计算两个用户的匹配得分
                double score = calculateMatchScore(users.get(i), users.get(j));
                // 将匹配结果添加到列表中
                matches.add(new Match(users.get(i), users.get(j), score));
            }
        }
        // 按匹配得分降序排序
        matches.sort((m1, m2) -> Double.compare(m2.score, m1.score));
        return matches;
    }

    /**
     * 计算两个用户的匹配得分
     * @param user1 用户1
     * @param user2 用户2
     * @return 匹配得分
     */
    private static double calculateMatchScore(User user1, User user2) {
        // 年龄差异得分，差异越小得分越高
        double ageScore = 1.0 / (1 + Math.abs(user1.age - user2.age));
        // 职业得分，相同职业得1分，否则0分
        double professionScore = user1.profession.equals(user2.profession) ? 1.0 : 0.0;
        // 共同景点得分
        double commonSpotsScore = calculateCommonSpotsScore(user1.favoriteSpots, user2.favoriteSpots);

        // 综合得分，由各部分得分按权重加权平均
        return ageScore * 0.4 + professionScore * 0.2 + commonSpotsScore * 0.4;
    }

    /**
     * 计算两个用户共同景点的比例
     * @param spots1 用户1的景点列表
     * @param spots2 用户2的景点列表
     * @return 共同景点得分
     */
    private static double calculateCommonSpotsScore(List<String> spots1, List<String> spots2) {
        Set<String> set1 = new HashSet<>(spots1);
        Set<String> set2 = new HashSet<>(spots2);
        set1.retainAll(set2); // 保留共同的景点
        // 共同景点数除以两个用户中景点较多的数量
        return (double) set1.size() / Math.max(spots1.size(), spots2.size());
    }
}
