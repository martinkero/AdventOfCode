package main

import (
	"os"
	"strings"
	"strconv"
	"../lib"
)

type Instruction interface {
	execute() bool
}

type snd struct{ register string }
type set struct {
	register string
	value    string
}
type add struct {
	register string
	value    string
}
type mul struct {
	register string
	value    string
}
type mod struct {
	register string
	value    string
}
type rcv struct{ register string }
type jgz struct {
	register string
	value    string
}

func (i snd) execute() bool {
	lastFreqPlayed = registers[i.register]
	return true
}
func (i set) execute() bool {
	value := getRegValue(i.value)
	registers[i.register] = value
	return true
}
func (i add) execute() bool {
	value := getRegValue(i.value)
	registers[i.register] += value
	return true
}
func (i mul) execute() bool {
	value := getRegValue(i.value)
	registers[i.register] *= value
	return true
}
func (i mod) execute() bool {
	value := getRegValue(i.value)
	registers[i.register] %= value
	return true
}
func (i rcv) execute() bool {
	if registers[i.register] > 0 {
		return false
	}
	return true
}
func (i jgz) execute() bool {
	if registers[i.register] > 0 {
		value := getRegValue(i.value)
		currentInstruction += value - 1
	}
	return true
}

func getRegValue(valueString string) int {
	value, err := strconv.Atoi(valueString)
	if err != nil {
		value = registers[valueString]
	}
	return value
}

func main() {
	testPart1()
	input := lib.ReadInputAsArray("Day18.in")
	println(part1(input))
}

var registers map[string]int
var currentInstruction int
var lastFreqPlayed int

func part1(input []string) int {
	registers = make(map[string]int)
	instructions := parseInstructions(input)
	currentInstruction = 0
	for currentInstruction <= len(instructions) {
		instruction := instructions[currentInstruction]
		if !instruction.execute() {
			break
		}
		currentInstruction++
	}
	return lastFreqPlayed
}

func parseInstructions(input []string) []Instruction {
	var instructions []Instruction
	for _, line := range input {
		slice := strings.Split(line, " ")
		if len(slice) > 2 {
			action, register, value := slice[0], slice[1], slice[2]
			switch action {
			case "set":
				instructions = append(instructions, set{register, value})
			case "add":
				instructions = append(instructions, add{register, value})
			case "mul":
				instructions = append(instructions, mul{register, value})
			case "mod":
				instructions = append(instructions, mod{register, value})
			case "jgz":
				instructions = append(instructions, jgz{register, value})
			}
		} else {
			action, register := slice[0], slice[1]
			switch action {
			case "snd":
				instructions = append(instructions, snd{register})
			case "rcv":
				instructions = append(instructions, rcv{register})
			}
		}
	}
	return instructions
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
	if result != expectedResult {
		println("Test failed, got", result, ", expected", expectedResult)
		os.Exit(1)
	}
}
