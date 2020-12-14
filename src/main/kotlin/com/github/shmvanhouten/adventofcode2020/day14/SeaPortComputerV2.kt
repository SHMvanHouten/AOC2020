package com.github.shmvanhouten.adventofcode2020.day14


class SeaPortComputerV2() {

    private val memory: MutableMap<Long, Long> = mutableMapOf()

    fun sumMemory(): Long {
        return memory.values.sum()
    }

    fun initialize(program: String) {
        for (block in splitIntoBlocksOfMasksAndInstructions(program)) {
            run(block)
        }
    }

    private fun run(block: Block) {
        block.permuteMaskXsTo0sAnd1s()
            .flatMap{ mask -> block.instructions.map { mask to it }}
            .forEach { (mask, instruction) ->
                writeToMemory(instruction, block, mask)
            }
    }

    private fun writeToMemory(
        instruction: Instruction,
        block: Block,
        mask: Mask
    ) {
        memory[instruction.address.applyMasks(block.baseMask, mask)] = instruction.value
    }

    private fun splitIntoBlocksOfMasksAndInstructions(program: String): List<Block> {
        return program.split("mask")
            .filter { it.isNotEmpty() }
            .map { toBlock(it) }
    }

}

private fun Long.applyMasks(baseMask: Long, floatingMask: Mask): Long =
    this.or(baseMask)
        .and(floatingMask.to0Mask.toLong(2))
        .or(floatingMask.to1Mask.toLong(2))
