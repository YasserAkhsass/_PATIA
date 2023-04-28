(define (problem sokoban)
	(:domain sokoban)
	(:objects 
		L -direction 
		U -direction 
		R -direction 
		D -direction 

		pos-0-0 - position
		pos-0-1 - position
		pos-0-2 - position
		pos-0-3 - position
		pos-0-4 - position
		pos-0-5 - position
		pos-1-0 - position
		pos-1-1 - position
		pos-1-2 - position
		pos-1-3 - position
		pos-1-4 - position
		pos-1-5 - position
		pos-2-0 - position
		pos-2-1 - position
		pos-2-2 - position
		pos-2-3 - position
		pos-2-4 - position
		pos-2-5 - position
		pos-3-0 - position
		pos-3-1 - position
		pos-3-2 - position
		pos-3-3 - position
		pos-3-4 - position
		pos-3-5 - position
		pos-4-0 - position
		pos-4-1 - position
		pos-4-2 - position
		pos-4-3 - position
		pos-4-4 - position
		pos-4-5 - position
		pos-5-0 - position
		pos-5-1 - position
		pos-5-2 - position
		pos-5-3 - position
		pos-5-4 - position
		pos-5-5 - position
		pos-6-0 - position
		pos-6-1 - position
		pos-6-2 - position
		pos-6-3 - position
		pos-6-4 - position
		pos-6-5 - position
		)
	(:init
		(adjacent pos-0-0 pos-0-1 R)
		(empty pos-0-0)
		(adjacent pos-0-1 pos-0-2 R)
		(empty pos-0-1)
		(adjacent pos-0-2 pos-0-3 R)
		(adjacent pos-0-3 pos-0-4 R)
		(adjacent pos-0-4 pos-0-5 R)
		(adjacent pos-0-5 pos-1-5 D)
		(adjacent pos-1-0 pos-1-1 R)
		(empty pos-1-0)
		(adjacent pos-1-1 pos-1-2 R)
		(empty pos-1-1)
		(adjacent pos-1-2 pos-1-3 R)
		(adjacent pos-1-3 pos-1-4 R)
		(empty pos-1-3)
		(adjacent pos-1-4 pos-1-5 R)
		(empty pos-1-4)
		(adjacent pos-1-5 pos-2-5 D)
		(adjacent pos-2-0 pos-2-1 R)
		(adjacent pos-2-1 pos-2-2 R)
		(adjacent pos-2-2 pos-2-3 R)
		(adjacent pos-2-3 pos-2-4 R)
		(empty pos-2-3)
		(adjacent pos-2-4 pos-2-5 R)
		(empty pos-2-4)
		(adjacent pos-2-5 pos-3-5 D)
		(adjacent pos-3-0 pos-3-1 R)
		(adjacent pos-3-1 pos-3-2 R)
		(empty pos-3-1)
		(adjacent pos-3-2 pos-3-3 R)
		(empty pos-3-2)
		(adjacent pos-3-3 pos-3-4 R)
		(block-in pos-3-3)
		(adjacent pos-3-4 pos-3-5 R)
		(empty pos-3-4)
		(adjacent pos-3-5 pos-4-5 D)
		(adjacent pos-4-0 pos-4-1 R)
		(adjacent pos-4-1 pos-4-2 R)
		(empty pos-4-1)
		(adjacent pos-4-2 pos-4-3 R)
		(adjacent pos-4-3 pos-4-4 R)
		(player-in pos-4-3)
		(adjacent pos-4-4 pos-4-5 R)
		(empty pos-4-4)
		(adjacent pos-4-5 pos-5-5 D)
		(adjacent pos-5-0 pos-5-1 R)
		(adjacent pos-5-1 pos-5-2 R)
		(empty pos-5-1)
		(adjacent pos-5-2 pos-5-3 R)
		(block-in pos-5-2)
		(adjacent pos-5-3 pos-5-4 R)
		(block-in pos-5-3)
		(adjacent pos-5-4 pos-5-5 R)
		(empty pos-5-4)
		(adjacent pos-5-5 pos-6-5 D)
		(adjacent pos-6-0 pos-6-1 R)
		(adjacent pos-6-1 pos-6-2 R)
		(adjacent pos-6-2 pos-6-3 R)
		(empty pos-6-2)
		(adjacent pos-6-3 pos-6-4 R)
		(empty pos-6-3)
		(adjacent pos-6-4 pos-6-5 R)
		(empty pos-6-4)
		(adjacent pos-6-5 pos-6-4 L)
		)
	(:goal
		(and
		(block-in pos-2-4)
		(block-in pos-3-3)
		(block-in pos-5-3)
		)
	)
)