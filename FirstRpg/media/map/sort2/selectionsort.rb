def sort(array)
	Integer i = 0
	Integer j = 0
	Integer min = 0
	while i < array.length() 
		j = i
		min = i
		while j < array.length() 
			if array.compare(min , j) then
				min = j
			end
			j = j + 1
		end
		array.exchange(i ,min)
		i = i + 1
	end
end