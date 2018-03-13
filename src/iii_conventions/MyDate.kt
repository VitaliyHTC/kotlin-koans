package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year > other.year -> 1
            year == other.year && month > other.month -> 1
            year == other.year && month == other.month && dayOfMonth > other.dayOfMonth -> 1
            year == other.year && month == other.month && dayOfMonth == other.dayOfMonth -> 0
            else -> -1
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currentDate = MyDate(start.year, start.month, start.dayOfMonth)

            override fun hasNext(): Boolean {
                return currentDate <= endInclusive
            }

            override fun next(): MyDate {
                val tmpDate = currentDate
                currentDate = currentDate.nextDay()
                return tmpDate
            }
        }
    }
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return this.addTimeIntervals(timeInterval, 1)
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

operator fun TimeInterval.times(times: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, times)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    return this.addTimeIntervals(repeatedTimeInterval.ti, repeatedTimeInterval.n)
}