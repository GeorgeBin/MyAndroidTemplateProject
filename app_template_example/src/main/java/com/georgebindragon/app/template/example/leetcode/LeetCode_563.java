package com.georgebindragon.app.template.example.leetcode;

/**
 * 创建人：George
 * 类名称：LeetCode_563
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


class LeetCode_563
{
	//	Definition for a binary tree node.
	static class TreeNode
	{
		int      val;
		TreeNode left;
		TreeNode right;

		TreeNode() {}

		TreeNode(int val) { this.val = val; }

		TreeNode(int val, TreeNode left, TreeNode right)
		{
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	int tilt = 0;

	public int findTilt(TreeNode root)
	{
		// 3. 参考官方解法，进行的优化
		dfs(root);
		return tilt;
	}

	public int dfs(TreeNode root)
	{
		if (null == root) return 0;

		int leftSum  = dfs(root.left);
		int rightSum = dfs(root.right);

		tilt += Math.abs(leftSum - rightSum);
		return leftSum + rightSum + root.val;
	}

/*

	// 2. 合适的特性
	int allTilt = 0;

	public int findTilt(TreeNode root)
	{
		if (null == root) return 0;

		// 1. 最直接的想法
		TreeNode left  = root.left;
		TreeNode right = root.right;

		int leftSum  = 0;
		int rightSum = 0;

		if (null != left)
		{
			leftSum = getSum(left);
		}
		if (null != right)
		{
			rightSum = getSum(right);
		}
		int currentTilt = Math.abs(leftSum - rightSum);

		allTilt += currentTilt;
		return allTilt;
	}

	public int getSum(TreeNode root)
	{
		if (null == root) return 0;

		int      sum   = 0;
		TreeNode left  = root.left;
		TreeNode right = root.right;

		int leftSum  = 0;
		int rightSum = 0;
		if (null != left)
		{
			leftSum = getSum(left);
			sum += leftSum;
		}
		if (null != right)
		{
			rightSum = getSum(right);
			sum += rightSum;
		}

		sum += root.val;
		int currentTilt = Math.abs(leftSum - rightSum);
		allTilt += currentTilt;

		return sum;
	}
}
 */


	/*
	// 1. 最直接的想法
		public int findTilt(TreeNode root)
		{
			if (null == root) return 0;
			int result = 0;

			TreeNode left  = root.left;
			TreeNode right = root.right;

			int leftTilt  = 0;
			int rightTilt = 0;

			int leftSum  = 0;
			int rightSum = 0;

			if (null != left)
			{
				leftTilt = findTilt(left);
				leftSum = getSum(left);
			}
			if (null != right)
			{
				rightTilt = findTilt(right);
				rightSum = getSum(right);
			}
			int currentTilt = Math.abs(leftSum - rightSum);

			result += leftTilt;
			result += rightTilt;
			result += currentTilt;
			if (null == left && null == right) return 0;
			return result;
		}

		public int getSum(TreeNode root)
		{
			if (null == root) return 0;

			int      sum   = 0;
			TreeNode left  = root.left;
			TreeNode right = root.right;

			int leftSum  = 0;
			int rightSum = 0;
			if (null != left)
			{
				leftSum = getSum(left);
				sum += leftSum;
			}
			if (null != right)
			{
				rightSum = getSum(right);
				sum += rightSum;
			}

			sum += root.val;

			return sum;
		}
	 */
}
