package lib

import (
	"os"
	"log"
	"bufio"
)

func ReadInputAsArray(fileName string) []string {
	file, err := os.Open(fileName)
	if err != nil {
		log.Fatal("Error opening input file")
	}
	scanner := bufio.NewScanner(file)
	var passphrases []string

	for scanner.Scan() {
		passphrases = append(passphrases, scanner.Text())
	}
	return passphrases
}
