@extends('layouts.admin')

@section('title', trans('reports.daily_summary'))

@section('new_button')
<!--<span class="new-button"><a href="{{ url('reports/profit-loss') }}?print=1&status={{ request('status') }}&year={{ request('year', $this_year) }}" target="_blank" class="btn btn-success btn-sm"><span class="fa fa-print"></span> &nbsp;{{ trans('general.print') }}</a></span>
-->
@endsection

@section('content')
<!-- Default box -->
<div class="box box-success">
    <div class="box-header with-border">
        {!! Form::open(['url' => 'reports/daily', 'role' => 'form', 'method' => 'GET']) !!}
        <div id="items" class="pull-left box-filter">
            {!! Form::date('fecha', Carbon\Carbon::now(),['class' => 'form-control input-filter-date input-sm']) !!}
            {!! Form::button('<span class="fa fa-filter"></span> &nbsp;' . trans('general.filter'), ['type' => 'submit', 'class' => 'btn btn-sm btn-default btn-filter']) !!}
        </div>
        {!! Form::close() !!}
    </div>
    
    <div class="table table-responsive table-report">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Factura</th>
            <th>Estado</th>
            <th>Monto</th>
          </tr>
        </thead>
        <tbody>
          @php
            $sum=0;
          @endphp
          @foreach ($data as $key => $value)
            @php
              $sum=$sum+$value->amount;
            @endphp
            <tr>
              <td>{{$value->invoice_number}}</td>
              @if ($value->invoice_status_code == 'paid')
              <td>Pagado</td>
              @endif
              @if ($value->invoice_status_code == 'partial')
              <td>Parcial</td>
              @endif
              @if ($value->invoice_status_code == 'draft')
              <td>Borrador</td>
              @endif
              @if ($value->invoice_status_code == 'received')
              <td>Recibido</td>
              @endif
              <td>{{$value->amount}}</td>
            </tr>
          @endforeach
        </tbody>
        <tfoot>
          <tr>
            <th colspan="2">Total de venta diario:</td>
            <th>{{$sum}}</td>
          </tr>
        </tfoot>
      </table>
    </div>
</div>
<!-- /.box -->
@endsection
