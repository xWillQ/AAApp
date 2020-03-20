fun validateVol(vol: Int?) = if (vol != null) vol > 0 else false

fun validateDate(date: String): Boolean {
    return date.matches(Regex("\\d{4}-((((0[13578])|(1[02]))-(0[1-9]|[12][0-9]|3[01]))|(((0[469])|(11))-(0[1-9]|[12][0-9]|(30)))|(02-(0[1-9]|[12][0-9])))"))
}