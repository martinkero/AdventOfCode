package main

import "fmt"

type stepper struct {
	posX      int
	posY      int
	direction string
}

func main() {
	stepper := stepper{0,0,"RIGHT"}
	stepsTaken := 0
	targetStep := 325489
	stepsPerTurn := 1
	turns := 0

	for stepsTaken < targetStep-1 {
		for step := 0; step < stepsPerTurn && stepsTaken < targetStep-1; step++ {
			takeStep(&stepper)
			stepsTaken++
		}

		turnLeft(&stepper)
		turns++
		if turns%2 == 0 {
			stepsPerTurn++
		}
	}

	result := 0
	if stepper.posX < 0 {
		result -= stepper.posX
	} else {
		result += stepper.posX
	}
	if stepper.posY < 0 {
		result -= stepper.posY
	} else {
		result += stepper.posY
	}
	fmt.Println(result)
}

func takeStep(stepper *stepper) {
	switch stepper.direction {
	case "RIGHT":
		stepper.posX++
	case "LEFT":
		stepper.posX--
	case "UP":
		stepper.posY++
	case "DOWN":
		stepper.posY--
	}
}

func turnLeft(stepper *stepper) {
	switch stepper.direction {
	case "RIGHT":
		stepper.direction = "UP"
	case "LEFT":
		stepper.direction = "DOWN"
	case "UP":
		stepper.direction = "LEFT"
	case "DOWN":
		stepper.direction = "RIGHT"
	}
}

