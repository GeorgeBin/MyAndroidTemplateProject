package com.georgebindragon.app.template.example.leetcode;

/**
 * 创建人：George
 * 类名称：LeetCode
 *
 * 描述：给定一个字符串数组 words，找到 length(word[i]) * length(word[j]) 的最大值，并且这两个单词不含有公共字母。你可以认为每个单词只包含小写字母。如果不存在这样的两个单词，返回 0。
 * 分析：
 * 不包含公共字母：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LeetCode_318
{
	public int maxProduct(String[] words)
	{
		int max = 0;

		// 3. 进行优化-->优化后，运行时间反而变长了
		char  base = 'a';
		int[] ints = new int[words.length];

		for (int i = 0; i < words.length; i++)
		{
			String word    = words[i];
			int    wordInt = 0;
			for (char s : word.toCharArray())
			{
				int move = s - base;
				wordInt |= (1 << move);
			}
			ints[i] = wordInt;

			for (int j = i - 1; j >= 0; j--)
			{
				int second = ints[j];
				// 判断两个字符是否包含相同内容
				if ((wordInt & second) == 0)
				{
					int temp = words[i].length() * words[j].length();
					if (temp > max) max = temp;
				}
			}
		}


		//		// 2. 思考巧妙的解法
		//		char  base = 'a';
		//		int[] ints = new int[words.length];
		//
		//		for (int i = 0; i < words.length; i++)
		//		{
		//			String word    = words[i];
		//			int    wordInt = 0;
		//			for (char s : word.toCharArray())
		//			{
		//				int move = s - base;
		//				wordInt |= (1 << move);
		//			}
		//			ints[i] = wordInt;
		//		}
		//
		//		for (int i = 0; i < words.length - 1; i++)
		//		{
		//			int first = ints[i];
		//
		//			for (int j = i + 1; j < words.length; j++)
		//			{
		//				int second = ints[j];
		//				// 判断两个字符是否包含相同内容
		//				if ((first & second) == 0)
		//				{
		//					int temp = words[i].length() * words[j].length();
		//					if (temp > max) max = temp;
		//				}
		//			}
		//		}


		//		// 1. 最直接的方式
		//		for (int i = 0; i < words.length; i++)
		//		{
		//			String first = words[i];
		//
		//			for (int j = i + 1; j < words.length; j++)
		//			{
		//				// 判断两个字符是否包含相同内容
		//				String  second   = words[j];
		//				boolean contains = false;
		//				for (char s : second.toCharArray())
		//				{
		//					contains = first.contains(String.valueOf(s));
		//					if (contains) break;
		//				}
		//				if (!contains)
		//				{
		//					int temp = first.length() * second.length();
		//					if (temp > max) max = temp;
		//				}
		//			}
		//		}

		return max;
	}
}
