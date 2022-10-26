package com.chauret.workoutgenerator.model.workout

import com.chauret.workoutgenerator.model.movement.Movement
import com.chauret.workoutgenerator.model.movement.WorkoutConfig
import java.util.UUID

class WorkoutFactory {
    fun createWorkout(config: WorkoutConfig, possibleMovements: List<Movement>): Workout {
        return Workout(
            guid = UUID.randomUUID(),
            config = config,
            exercises = createExercises(config, possibleMovements),
            timestampMillis = System.currentTimeMillis()
        )
    }

    private fun createExercises(config: WorkoutConfig, possibleMovements: List<Movement>): List<Exercise> {
        val legalMovements: MutableSet<Movement> = possibleMovements.filter {
            it.workoutTypes.intersect(config.workoutTypes).isNotEmpty()
        }.toMutableSet()
        val numExercises = (minOf(config.minExercises, legalMovements.size)..minOf(config.maxExercises, legalMovements.size)).random()
        val exerciseFactory = ExerciseFactory()
        return (0 until numExercises).map {
            val movement = legalMovements.random()
            legalMovements.remove(movement)
            exerciseFactory.createExercise(movement)
        }
    }
}
