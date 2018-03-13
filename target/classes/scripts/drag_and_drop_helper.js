(function($) {
	$.fn.simulateDragDrop = function(options) {
		return this.each(function() {
			new $.simulateDragDrop(this, options);
		});
	};
	$.simulateDragDrop = function(elem, options) {
		this.options = options;
		this.simulateEvent(elem, options);
	};
	$.extend($.simulateDragDrop.prototype, {
		simulateEvent : function(elem, options) {
			
				/* Simulating mousedown */
				var type = 'mousedown';
				var event = this.createEvent(type);
				this.dispatchEvent(elem, type, event);
				
				setTimeout(this.delayEvent, 5000);

				/* Simulating dragenter */
				type = 'dragenter';
				var dragenterEvent = this.createEvent(type, {});
				dragenterEvent.dataTransfer = event.dataTransfer;
				this.dispatchEvent(elem, type, dragenterEvent);

				/* Simulating dragover */
				type = 'dragover';
				var dragoverEvent = this.createEvent(type, {});
				dragoverEvent.dataTransfer = event.dataTransfer;
				this.dispatchEvent($(options.dropTarget)[0], type,
						dragoverEvent);

				/* Simulating drop */
				type = 'drop';
				var dropEvent = this.createEvent(type, {});
				dropEvent.dataTransfer = event.dataTransfer;
				this.dispatchEvent($(options.dropTarget)[0], type, dropEvent);
		},
		createEvent : function(type) {
			var event = document.createEvent("CustomEvent");
			event.initCustomEvent(type, true, true, null);
			event.dataTransfer = {
				data : {},
				setData : function(type, val) {
					this.data[type] = val;
				},
				getData : function(type) {
					return this.data[type];
				}
			};
			return event;
		},
		dispatchEvent : function(elem, type, event) {
			if (elem.dispatchEvent) {
				elem.dispatchEvent(event);
			} else if (elem.fireEvent) {
				elem.fireEvent("on" + type, event);
			}
		},
		delayEvent : function ()	{
					console.log('holding mouse for 5 seconds');
					alert("Hello");
		}
	});
})(jQuery);