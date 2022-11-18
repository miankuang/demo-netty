package com.netty.netty;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class NettyDemoApplicationTests {


	public static void main(String[] args) {
		ArrayList<Integer> integers = ListUtil.toList(1, 2, 4);
		List<Integer> result = integers.stream().filter(i -> i > 5).collect(Collectors.toList());
		System.out.println(JSONUtil.toJsonStr(result));
	}

}
