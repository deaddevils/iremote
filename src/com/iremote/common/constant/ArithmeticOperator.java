package com.iremote.common.constant;

public enum ArithmeticOperator
{
	eq {
		@Override
		public boolean compare(int v1, int v2)
		{
			return v1 == v2;
		}
	},
	lt{
		@Override
		public boolean compare(int v1, int v2)
		{
			return v1 < v2;
		}
	},
	le{
		@Override
		public boolean compare(int v1, int v2)
		{
			return v1 <= v2;
		}
	},
	gt{
		@Override
		public boolean compare(int v1, int v2)
		{
			return v1 > v2;
		}
	},
	ge{
		@Override
		public boolean compare(int v1, int v2)
		{
			return v1 >= v2;
		}
	};
	
	public abstract boolean compare(int v1 , int v2);
	
}
