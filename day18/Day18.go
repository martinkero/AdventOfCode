package main

import "os"

func main() {
	testPart1()
}

func part1(input []string) int {
	value := 0
	
	return value
}

func testPart1() {
	input := []string{
		"set a 1",
		"add a 2",
		"mul a a",
		"mod a 5",
		"snd a",
		"set a 0",
		"rcv a",
		"jgz a -1",
		"set a 1",
		"jgz a -2",
	}
	result := part1(input)
	expectedResult := 4
	if (result!=expectedResult) {
		println("Test failed, got", result, ", expected", expectedResult)
		os.Exit(1)
	}
}