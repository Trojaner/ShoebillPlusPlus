/*
 * Copyright (c) 2013 - 2014 <http://static-interface.de> and contributors
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.static_interface.shoebillplusplus.api.event.dialog;

import de.static_interface.shoebillplusplus.api.event.Cancellable;
import de.static_interface.shoebillplusplus.api.event.Processable;

public class DialogResponseEvent extends DialogEvent<net.gtaun.shoebill.event.dialog.DialogResponseEvent>
                                 implements Cancellable, Processable {
    private boolean cancelled;
    private boolean processed;
    private int dialogResponse;
    private int listitem;
    private String inputText;

    public DialogResponseEvent(net.gtaun.shoebill.event.dialog.DialogResponseEvent base) {
        super(base);
        dialogResponse = getBase().getDialogResponse();
        listitem = getBase().getListitem();
        inputText = getBase().getInputText();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        if(processed) {
            // Todo: throw exception?
            return;
        }
        cancelled = cancel;
    }

    @Override
    public void setProcessed() {
        getBase().setProcessed();
        processed = true;
        setCancelled(true);
    }

    @Override
    public boolean isProcessed() {
        return processed;
    }

    public int getResponse() {
        return getBase().getResponse();
    }

    public int getDialogResponse() {
        return dialogResponse;
    }

    public void setDialogResponse(int dialogResponse) {
        this.dialogResponse = dialogResponse;
    }

    public int getListitem() {
        return listitem;
    }

    public String getInputText() {
        return inputText;
    }
}
