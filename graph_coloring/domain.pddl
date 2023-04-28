(define (domain graphecolor)
	(:requirements :strips :typing :equality)
	(:types state
			color)
	(:predicates
				(link ?x - state ?y - state)
				(notSame ?c1 - color ?c2 - color)
				(unlink ?x - state ?y - state)
				(clored ?x - state ?c - color)
				(ncolored ?x - state)
	)

	(:action toColor
			:parameters (?state - state ?color - color)
			:precondition (and (ncolored ?state))
			:effect
				(and (not (ncolored ?state))(clored ?state ?color))
	)

	(:action clear
			:parameters (?state1 - state ?state2 - state ?c1 - color ?c2 - color )
			:precondition ( and (clored  ?state1 ?c1 )(clored  ?state2 ?c2) (link ?state1 ?state2 ) (notSame ?c1 ?c2))
			:effect
			(and (unlink ?state1 ?state2))
	)
)
